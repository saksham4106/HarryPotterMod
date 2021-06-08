//package com.saksham.pottercraftmod.core.world.features;
//
//import java.util.Random;
//import java.util.Set;
//import java.util.function.Function;
//
//import com.mojang.datafixers.Dynamic;
//import com.saksham.pottercraftmod.common.init.BlockInit;
//
//import net.minecraft.block.BlockState;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MutableBoundingBox;
//import net.minecraft.world.gen.IWorldGenerationReader;
//import net.minecraft.world.gen.feature.AbstractTreeFeature;
//import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
//
//public class ForbiddenTreeFeature extends AbstractTreeFeature<BaseTreeFeatureConfig>{
//
//	private static final BlockState TRUNK = BlockInit.FORBIDDEN_LOG.get().getDefaultState();
//    //private static final BlockState LEAF = BlockInit.FORBIDDEN_LEAVES.get().getDefaultState();
//
//	public ForbiddenTreeFeature(Function<Dynamic<?>, ? extends BaseTreeFeatureConfig> config) {
//		super(config);
//	}
//
//	@Override
//	protected boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos pos,
//			Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_	, MutableBoundingBox boundingBoxIn,
//			BaseTreeFeatureConfig configIn) {
//
//		int variedHeight1 = rand.nextInt(3) + 4  + rand.nextInt(3) + 7;
//
//		for(int j = 0; j <= variedHeight1 - 2; j++) {
//			setBlockState(generationReader,pos.add(0, j, 0) , TRUNK);
//		}
//		return false;
//	}
//
//
//}
