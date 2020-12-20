package com.saksham.pottercraftmod.objects.items;

import com.saksham.pottercraftmod.PottercraftMod;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlooPowderItem extends Item {

	public FlooPowderItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		BlockPos pos = playerIn.getPosition();

		if(checkXbyZ(pos.down().north(2).west(2), worldIn)) {
			if(checkXbyZ(pos.up(3).north(2).west(2), worldIn)) {
				if(test(pos, worldIn)) {
					PottercraftMod.LOGGER.info("POG");
				}					
			}
		}
//		if(checkXbyY(pos.down().north(2).west(2), worldIn, false)) {
//			if(checkXbyY(pos.down().east(2).north(2), worldIn, true)) {
//				if(checkXbyY(pos.down().west(2).south(2), worldIn, false)) {
//					if(checkXbyY(pos.down().south(2).west(2), worldIn, false)) {
//		
		
		playerIn.getHeldItemMainhand().shrink(1);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	private boolean test(BlockPos pos, World worldIn) {
		int count = 0;
//
//		if(checkXbyY(pos.down().north(2).west(2), worldIn, false, false)) {
//			System.out.println(" -> " + 1);
//			count++;
//		}
//		if(checkXbyY(pos.down().west(2).north(2), worldIn, true, false)) {
//			System.out.println(" -> " + 2);
//			count++;
//		}
		if(checkXbyY(pos.down().west(2).south(2), worldIn, false, true)) {
			System.out.println(" -> " + 3);
			count++;
		}
		if(checkXbyY(pos.down().south(2).west(2), worldIn, true, false)) {
			System.out.println(" -> " + 4);
			count++;
		}
		System.out.println("this is count -> "+ count);
		if(count==3) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	private boolean checkXbyZ(BlockPos pos, World worldIn) {
		int count = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(worldIn.getBlockState(pos.add(i, 0, j)) == Blocks.WHITE_CONCRETE.getDefaultState()) {
					count++;
				}
			}
		}
		if(count==25) {
			return true;
		}else {
			return false;
		}
	}
	
	
	private boolean checkXbyY(BlockPos pos, World worldIn, boolean xOrZ, boolean y) {
		int count = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(y)
				worldIn.setBlockState(pos.add(i, j, 0), Blocks.WHITE_CONCRETE.getDefaultState());
				
				if(!xOrZ) {
					if(worldIn.getBlockState(pos.add(i, j, 0)) ==  Blocks.WHITE_CONCRETE.getDefaultState()) {
						count++;
						
					}
				}else {
					if(worldIn.getBlockState(pos.add(0, j, i)) == Blocks.WHITE_CONCRETE.getDefaultState()) {
						count++;
					}
				}
				
			}
		}
		
		if(count==25) {
			return true;
		}else {
			return false;
		}
	}

}
