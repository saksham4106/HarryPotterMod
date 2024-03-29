package com.saksham.pottercraftmod.common.blocks;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.saksham.pottercraftmod.client.ScreenHandler;
import com.saksham.pottercraftmod.common.registry.BlockInit;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FlooFireBlock extends Block{
    private boolean isAllowed = true;
 
    public FlooFireBlock(Block.Properties properties) {
      super(properties.tickRandomly());
        this.setDefaultState(
            this.stateContainer.getBaseState().with(FireBlock.AGE, 0).with(FireBlock.NORTH, Boolean.FALSE)
                .with(FireBlock.EAST, Boolean.FALSE).with(FireBlock.SOUTH, Boolean.FALSE).with(FireBlock.WEST,
                    Boolean.FALSE).with(FireBlock.UP, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return VoxelShapes.empty();
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
      Block block = state.getBlock();
      return block == BlockInit.FLOO_FIRE_BLOCK.get();
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
      if(!(worldIn.getBlockState(pos.down()) == BlockInit.FLOO_FIRE_BLOCK.get().getDefaultState())) {
          worldIn.removeBlock(pos, false);
      }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(FireBlock.AGE, FireBlock.NORTH, FireBlock.EAST, FireBlock.SOUTH, FireBlock.WEST, FireBlock.UP);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if(worldIn.isRemote) {
            if(entityIn instanceof PlayerEntity) {
                if(isAllowed && Minecraft.getInstance().currentScreen == null){
                    ScreenHandler.displayFlooTeleportScreen(entityIn, pos);
                    this.isAllowed = false;
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            isAllowed = true;
                        }
                    }, 3 * 1000);
                }
            }
        }
    }
}
