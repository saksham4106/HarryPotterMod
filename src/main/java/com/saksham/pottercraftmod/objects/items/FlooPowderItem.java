package com.saksham.pottercraftmod.objects.items;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.init.BlockInit;

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

		if(checkXbyX(worldIn, pos.down().north(2).west(2)) &&
			checkXbyX(worldIn, pos.up(3).north(2).west(2)) &&
			checkFor3Wall(worldIn, pos) == 3

			) {
			System.out.println("pog");
			
		}else {
			worldIn.setBlockState(pos, BlockInit.FLOO_FIRE_BLOCK.get().getDefaultState());
		}
		
		
		playerIn.getHeldItemMainhand().shrink(1);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	private int checkFor3Wall(World worldIn, BlockPos pos) {
		int count = 0;
		if(checkZbyY(worldIn, pos.down().north(2).west(2))) {
			count++;
		}
		if(checkZbyY(worldIn, pos.down().north(2).east(2))) {
			count++;
		}
		if(checkXbyY(worldIn, pos.down().south(2).west(2))) {
			count++;
		}
		if(checkXbyY(worldIn, pos.down().north(2).west(2))) {
			count++;
			System.out.println("yeet");
		}
		
//		if(checkXbyY(worldIn, pos)) {
//			count++;
//		}w
		
		
		System.out.println(count);
		return count;
	}

	
	private boolean checkZbyY(World worldIn, BlockPos pos) {
		boolean flag = true;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				//PottercraftMod.LOGGER.info("this is the player pos -> " + pos + "it is checking this -> " + pos.add(0, i, j) + "value of j -> " + j);
				worldIn.setBlockState(pos.add(0, i, j), Blocks.WHITE_CONCRETE.getDefaultState());
				if(worldIn.getBlockState(pos.add(0, i, j)) == BlockInit.MINISTRY_OF_MAGIC_BRICK.get().getDefaultState()) {
					flag = true;
				}else {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	private boolean checkXbyY(World worldIn, BlockPos pos) {
		boolean flag = true;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				worldIn.setBlockState(pos.add(0, i, j), Blocks.WHITE_CONCRETE.getDefaultState());
				PottercraftMod.LOGGER.info("this is the player pos -> " + pos + "it is checking this -> " + pos.add(j, i, 0) + "value of j -> " + j);
				if(worldIn.getBlockState(pos.add(j, i, 0)) == BlockInit.MINISTRY_OF_MAGIC_BRICK.get().getDefaultState()) {
					flag = true;
				}else {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	
	private boolean checkXbyX(World worldIn, BlockPos pos ) {
		
		boolean flag = true;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				worldIn.setBlockState(pos.add(0, i, j), Blocks.WHITE_CONCRETE.getDefaultState());
				//PottercraftMod.LOGGER.info("a) this is the player pos -> " + pos + "it is checking this -> " + pos.add(i, 0, j) + "value of j -> " + j);
				if(worldIn.getBlockState(pos.add(i, 0, j)) == BlockInit.MINISTRY_OF_MAGIC_BRICK.get().getDefaultState()) {
					flag = true;
				}else {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

}
