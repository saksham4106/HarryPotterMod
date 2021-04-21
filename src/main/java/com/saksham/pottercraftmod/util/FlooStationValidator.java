package com.saksham.pottercraftmod.util;

import com.saksham.pottercraftmod.init.BlockInit;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlooStationValidator {
	
	/*
	 * the pos should be the center of the station
	 */
	public static boolean isFlooStationValid(BlockPos pos, World worldIn) {
		if(checkXbyZ(pos.down().north(2).west(2), worldIn) == 25) {
			if(checkXbyZ(pos.up(3).north(2).west(2), worldIn) == 25) {
				int count = 0;
				if(checkXbyY(pos.down().south(2).west(2), worldIn) == 25) { count++;}
				if(checkXbyY(pos.down().north(2).west(2), worldIn) == 25) { count++;}
				if(checkZbyY(pos.down().north(2).west(2), worldIn) == 25) { count++;}
				if(checkZbyY(pos.down().north(2).east(2), worldIn) == 25) { count++;}
				
				
				if(count == 3) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static int checkXbyZ(BlockPos pos, World world) {
		int count = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(world.getBlockState(pos.add(i, 0, j)) == BlockInit.MINISTRY_OF_MAGIC_BRICK.get().getDefaultState()) {
					count++;
				}
			}
		}
		return count;
	}
	
	private static int checkXbyY(BlockPos pos, World world) {
		int count = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(world.getBlockState(pos.add(i, j, 0)) == BlockInit.MINISTRY_OF_MAGIC_BRICK.get().getDefaultState()) {
					count++;
				}
			}
		}
		return count;
	}
	
	private static int checkZbyY(BlockPos pos, World world) {
	    int count = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(world.getBlockState(pos.add(0, j, i)) == BlockInit.MINISTRY_OF_MAGIC_BRICK.get().getDefaultState()) {
					count++;
				}
			}
		}
		return count;
	}


	
}