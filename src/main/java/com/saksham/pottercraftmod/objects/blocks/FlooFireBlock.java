package com.saksham.pottercraftmod.objects.blocks;

import java.util.Random;

import com.saksham.pottercraftmod.init.BlockInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SixWayBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlooFireBlock extends Block{

	 public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;
	 public static final BooleanProperty NORTH = SixWayBlock.NORTH;
	 public static final BooleanProperty EAST = SixWayBlock.EAST;
	 public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
	 public static final BooleanProperty WEST = SixWayBlock.WEST;
	 public static final BooleanProperty UP = SixWayBlock.UP;
	
 
  public FlooFireBlock(Block.Properties properties) {
	  super(properties.tickRandomly());
		this.setDefaultState(
				this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)).with(NORTH, Boolean.valueOf(false)).with(EAST, Boolean.valueOf(false)).with(SOUTH, Boolean.valueOf(false)).with(WEST, Boolean.valueOf(false)).with(UP, Boolean.valueOf(false)));
  }

  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return VoxelShapes.empty();
   }
  
  protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
      Block block = state.getBlock();
      return block == BlockInit.FLOO_FIRE_BLOCK.get();
   }
  
  @SuppressWarnings("deprecation")
public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }
  public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      BlockPos blockpos = pos.down();
       return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
  }




   public int tickRate(IWorldReader worldIn) {
      return 30;
   }
   
   

   @Override
   public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
	  if(!(worldIn.getBlockState(pos.down()) == BlockInit.FLOO_FIRE_BLOCK.get().getDefaultState())) {
		  System.out.println("yes");
		  worldIn.removeBlock(pos, false);
	  }
      if (worldIn.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
         if (!worldIn.isAreaLoaded(pos, 2)) return; // Forge: prevent loading unloaded chunks when spreading fire
         
         BlockState other = worldIn.getBlockState(pos.down());
         boolean flag = other.isFireSource(worldIn, pos.down(), Direction.UP);
         int i = state.get(AGE);
   
        int j = Math.min(15, i + rand.nextInt(3) / 2);
        if (i != j) {
           state = state.with(AGE, Integer.valueOf(j));
           worldIn.setBlockState(pos, state, 4);
        }

        if (!flag) {
           worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn) + rand.nextInt(10));
        }

  
        
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(int l = -1; l <= 1; ++l) {
           for(int i1 = -1; i1 <= 1; ++i1) {
              for(int j1 = -1; j1 <= 4; ++j1) {
                 if (l != 0 || j1 != 0 || i1 != 0) {
                    @SuppressWarnings("unused")
					int k1 = 100;
                    if (j1 > 1) {
                       k1 += (j1 - 1) * 100;
                    }

                    blockpos$mutable.setPos(pos).move(l, j1, i1);
       
                    }
                 }
              }
           }
      	}  
   }

   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (oldState.getBlock() != state.getBlock()) {
         if (worldIn.dimension.getType() != DimensionType.OVERWORLD && worldIn.dimension.getType() != DimensionType.THE_NETHER) {
            if (!state.isValidPosition(worldIn, pos)) {
               worldIn.removeBlock(pos, false);
            } else {
               worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn) + worldIn.rand.nextInt(10));
            }
         }
      }
   }

   @SuppressWarnings("unused")
   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (rand.nextInt(24) == 0) {
         worldIn.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
      }

      BlockPos blockpos = pos.down();
      BlockState blockstate = worldIn.getBlockState(blockpos);


         for(int i = 0; i < 3; ++i) {
            double d0 = (double)pos.getX() + rand.nextDouble();
            double d1 = (double)pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
            double d2 = (double)pos.getZ() + rand.nextDouble();
            //worldIn.addParticle(new WandSpellParticle.WandSpellParticleData(0.1f, 0.1f, 0.1f, 0.1f), d0, d1, d2, 0.0D, 0.0D, 0.0D);
      
      }

   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(AGE, NORTH, EAST, SOUTH, WEST, UP);
   }


	

}
