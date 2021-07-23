package com.saksham.pottercraftmod.common.blocks;

import java.util.Map;
import java.util.Random;

import com.saksham.pottercraftmod.common.registry.BlockInit;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FlooFireBaseBlock extends FireBlock{

	private static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((p_199776_0_) -> p_199776_0_.getKey() != Direction.DOWN).collect(Util.toMapCollector());
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if(worldIn.getBlockState(pos.up()).getBlock() instanceof AirBlock) {
			worldIn.setBlockState(pos.up(), BlockInit.FLOO_FIRE.get().getDefaultState());
		}else {
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
	}

	public FlooFireBaseBlock(Properties builder) {
		super(builder.noDrops().notSolid().setLightLevel((blockState) -> 6));
		this.setDefaultState(
				this.stateContainer.getBaseState().with(AGE, 0).with(NORTH, Boolean.FALSE)
						.with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE)
						.with(WEST, Boolean.FALSE).with(UP, Boolean.FALSE));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 0.2D, 16.0D);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {

		return this.isValidPosition(stateIn, worldIn, currentPos)
				? this.getStateForPlacement(worldIn, currentPos).with(AGE, stateIn.get(AGE))
				: Blocks.AIR.getDefaultState();
	}

	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (worldIn.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
			if (!worldIn.isAreaLoaded(pos, 2))
				return;

			BlockState other = worldIn.getBlockState(pos.down());
			boolean flag = other.isFireSource(worldIn, pos.down(), Direction.UP);
			int i = state.get(AGE);
			if (!flag && worldIn.isRaining() && this.canDie(worldIn, pos)
					&& rand.nextFloat() < 0.2F + (float) i * 0.03F) {
				int j = Math.min(15, i + rand.nextInt(3) / 2);
				if (i != j) {
					state = state.with(AGE, j);
					worldIn.setBlockState(pos, state, 4);
				}
			}
		}
	}

	/*
		Changes access
	 */
	public BlockState getStateForPlacement(IBlockReader blockReader, BlockPos pos) {
		BlockPos blockpos = pos.down();
		BlockState blockstate = blockReader.getBlockState(blockpos);
		if (!this.canCatchFire(blockReader, pos, Direction.UP) && !blockstate.isSolidSide(blockReader, blockpos, Direction.UP)) {
			BlockState blockstate1 = this.getDefaultState();

			for(Direction direction : Direction.values()) {
				BooleanProperty booleanproperty = FACING_TO_PROPERTY_MAP.get(direction);
				if (booleanproperty != null) {
					blockstate1 = blockstate1.with(booleanproperty, this.canCatchFire(blockReader, pos.offset(direction), direction.getOpposite()));
				}
			}

			return blockstate1;
		} else {
			return this.getDefaultState();
		}
	}

	protected boolean canDie(World worldIn, BlockPos pos) {
		return worldIn.isRainingAt(pos) || worldIn.isRainingAt(pos.west()) || worldIn.isRainingAt(pos.east())
				|| worldIn.isRainingAt(pos.north()) || worldIn.isRainingAt(pos.south());
	}

	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(24) == 0) {
			worldIn.playSound( ((float) pos.getX() + 0.5F), ((float) pos.getY() + 0.5F),
					((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS,
					1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
		}

		BlockPos blockpos = pos.down();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		if (!this.canCatchFire(worldIn, blockpos, Direction.UP)
				&& !blockstate.isSolidSide(worldIn, blockpos, Direction.UP)) {
			if (this.canCatchFire(worldIn, blockpos.west(), Direction.EAST)) {
				for (int j = 0; j < 2; ++j) {
					double d3 = (double) pos.getX() + rand.nextDouble() * (double) 0.1F;
					double d8 = (double) pos.getY() + rand.nextDouble();
					double d13 = (double) pos.getZ() + rand.nextDouble();
					worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d3, d8, d13, 0.0D, 0.0D, 0.0D);
				}
			}

			if (this.canCatchFire(worldIn, pos.east(), Direction.WEST)) {
				for (int k = 0; k < 2; ++k) {
					double d4 = (double) (pos.getX() + 1) - rand.nextDouble() * (double) 0.1F;
					double d9 = (double) pos.getY() + rand.nextDouble();
					double d14 = (double) pos.getZ() + rand.nextDouble();
					worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d4, d9, d14, 0.0D, 0.0D, 0.0D);
				}
			}

			if (this.canCatchFire(worldIn, pos.north(), Direction.SOUTH)) {
				for (int l = 0; l < 2; ++l) {
					double d5 = (double) pos.getX() + rand.nextDouble();
					double d10 = (double) pos.getY() + rand.nextDouble();
					double d15 = (double) pos.getZ() + rand.nextDouble() * (double) 0.1F;
					worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d5, d10, d15, 0.0D, 0.0D, 0.0D);
				}
			}

			if (this.canCatchFire(worldIn, pos.south(), Direction.NORTH)) {
				for (int i1 = 0; i1 < 2; ++i1) {
					double d6 = (double) pos.getX() + rand.nextDouble();
					double d11 = (double) pos.getY() + rand.nextDouble();
					double d16 = (double) (pos.getZ() + 1) - rand.nextDouble() * (double) 0.1F;
					worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d6, d11, d16, 0.0D, 0.0D, 0.0D);
				}
			}

			if (this.canCatchFire(worldIn, pos.up(), Direction.DOWN)) {
				for (int j1 = 0; j1 < 2; ++j1) {
					double d7 = (double) pos.getX() + rand.nextDouble();
					double d12 = (double) (pos.getY() + 1) - rand.nextDouble() * (double) 0.1F;
					double d17 = (double) pos.getZ() + rand.nextDouble();
					worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
				}
			}
		} else {
			for (int i = 0; i < 3; ++i) {
				double d0 = (double) pos.getX() + rand.nextDouble();
				double d1 = (double) pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
				double d2 = (double) pos.getZ() + rand.nextDouble();
				worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}
		}

	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AGE, NORTH, EAST, SOUTH, WEST, UP);
	}

	public boolean canCatchFire(IBlockReader world, BlockPos pos, Direction face) {
		return false;
	}
}
