package com.saksham.pottercraftmod.common.items;

import com.saksham.pottercraftmod.common.blocks.FlooFireBaseBlock;
import com.saksham.pottercraftmod.common.init.BlockInit;
import com.saksham.pottercraftmod.core.util.FlooStationValidator;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class FlooPowderItem extends Item {

	public FlooPowderItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		BlockPos pos = playerIn.getPosition();
		if(FlooStationValidator.isFlooStationValid(pos, worldIn)) {
			worldIn.setBlockState(pos, BlockInit.FLOO_FIRE_BLOCK.get().getDefaultState());
			worldIn.setBlockState(pos.up(), BlockInit.FLOO_FIRE.get().getDefaultState());
			worldIn.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
		}
		
		playerIn.getHeldItemMainhand().shrink(1);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		BlockPos pos = context.getPos().offset(context.getFace());
		World world = context.getWorld();

		if(canSetFlooFire(world.getBlockState(pos), world, pos)){
			BlockState blockState = ((FlooFireBaseBlock)BlockInit.FLOO_FIRE_BLOCK.get()).getStateForPlacement(world, pos);
			world.setBlockState(pos, blockState, 11);
			world.setBlockState(pos.up(), BlockInit.FLOO_FIRE.get().getDefaultState());
			world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);

			return ActionResultType.SUCCESS;
		}else{
			return ActionResultType.FAIL;
		}
	}

	public static boolean canSetFlooFire(BlockState existingState, IWorld worldIn, BlockPos posIn) {
		BlockState blockstate = ((FireBlock) Blocks.FIRE).getStateForPlacement(worldIn, posIn);
		return existingState.isAir(worldIn, posIn) && (blockstate.isValidPosition(worldIn, posIn));
	}
	

}
