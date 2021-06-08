package com.saksham.pottercraftmod.common.blocks;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.saksham.pottercraftmod.common.tileentity.NameplateTileEntity;

import com.saksham.pottercraftmod.core.network.FlooNetworkSavedData;
import com.saksham.pottercraftmod.core.util.FlooStationValidator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class NameplateBlock extends WallSignBlock{
	 private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(0.0D, 4.5D, 14.0D, 16.0D, 12.5D, 16.0D), Direction.SOUTH, Block.makeCuboidShape(0.0D, 4.5D, 0.0D, 16.0D, 12.5D, 2.0D), Direction.EAST, Block.makeCuboidShape(0.0D, 4.5D, 0.0D, 2.0D, 12.5D, 16.0D), Direction.WEST, Block.makeCuboidShape(14.0D, 4.5D, 0.0D, 16.0D, 12.5D, 16.0D)));

	public NameplateBlock(Properties properties) {
		super(properties, WoodType.OAK);
		this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
		
	}


	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES.get(state.get(FACING));
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		System.out.println("updatePostPlacement");
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		pos = pos.down(3);
		FlooNetworkSavedData wsd = FlooNetworkSavedData.get(worldIn);
		if (FlooStationValidator.isFlooStationValid(pos.south(3), worldIn)) {
			wsd.removeFlooStation(pos.south(3));
		} else if (FlooStationValidator.isFlooStationValid(pos.north(3), worldIn)) {
			wsd.removeFlooStation(pos.north(3));
		} else if (FlooStationValidator.isFlooStationValid(pos.west(3), worldIn)) {
			wsd.removeFlooStation(pos.west(3));
		} else if (FlooStationValidator.isFlooStationValid(pos.east(3), worldIn)) {
			wsd.removeFlooStation(pos.east(3));
		} else{
			System.out.println("No FlooStation linked to the nameplate found to Remove");
		}
		super.onBlockHarvested(worldIn, pos, state, player);
}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new NameplateTileEntity();
	}

}
