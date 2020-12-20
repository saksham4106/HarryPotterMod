package com.saksham.pottercraftmod.objects.items;

import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class NameplateItem extends BlockItem{

	   protected final Block wallBlock;
	   
	public NameplateItem(Properties builder, Block wallBlockIn) {
		super(wallBlockIn,  builder);
		this.wallBlock = wallBlockIn;
	}

	 @Nullable
	   protected BlockState getStateForPlacement(BlockItemUseContext context) {
	      BlockState blockstate = this.wallBlock.getStateForPlacement(context);
	      BlockState blockstate1 = null;
	      IWorldReader iworldreader = context.getWorld();
	      BlockPos blockpos = context.getPos();

	      for(Direction direction : context.getNearestLookingDirections()) {
	         if (direction != Direction.UP) {	
	            BlockState blockstate2 = direction == Direction.DOWN ? this.getBlock().getStateForPlacement(context) : blockstate;
	            if (blockstate2 != null && blockstate2.isValidPosition(iworldreader, blockpos)) {
	               blockstate1 = blockstate2;
	               break;
	            }
	         }
	      }

	      return blockstate1 != null && iworldreader.placedBlockWouldCollide(blockstate1, blockpos, ISelectionContext.dummy()) ? blockstate1 : null;
	   }
	 
	 public void addToBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
	      super.addToBlockToItemMap(blockToItemMap, itemIn);
	      blockToItemMap.put(this.wallBlock, itemIn);
	   }

	   public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
	      super.removeFromBlockToItemMap(blockToItemMap, itemIn);
	      blockToItemMap.remove(this.wallBlock);
	   }
	   

	   protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
	      boolean flag = super.onBlockPlaced(pos, worldIn, player, stack, state);

	      
	      if (!worldIn.isRemote && !flag && player != null && player instanceof ServerPlayerEntity) {
	    	  
	         player.openSignEditor((SignTileEntity)worldIn.getTileEntity(pos));
	      }

	      return flag;
	   }
}
