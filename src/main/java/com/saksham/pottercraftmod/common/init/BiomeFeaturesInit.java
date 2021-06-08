//package com.saksham.pottercraftmod.common.init;
//
//import com.saksham.pottercraftmod.core.world.biomes.ForbiddenBiomeSurfaceBuilder;
//import com.saksham.pottercraftmod.core.world.features.ForbiddenTreeFeature;
//
//import net.minecraft.util.registry.Registry;
//import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
//import net.minecraft.world.gen.feature.Feature;
//import net.minecraft.world.gen.feature.IFeatureConfig;
//import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
//import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
//
//public class BiomeFeaturesInit {
//
//	public static final SurfaceBuilder<SurfaceBuilderConfig> FORBIDDEN_SURFACE_BUILDER = new ForbiddenBiomeSurfaceBuilder(SurfaceBuilderConfig::deserialize);
//	public static final Feature<BaseTreeFeatureConfig> FORBIDDEN_TREE = register("forbidden_tree", new ForbiddenTreeFeature(BaseTreeFeatureConfig::deserialize));
//
//
//
//	 @SuppressWarnings("deprecation" )
//	private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value)
//
//	    {
//	        return Registry.register(Registry.FEATURE, key, value);
//	    }
//
//}
