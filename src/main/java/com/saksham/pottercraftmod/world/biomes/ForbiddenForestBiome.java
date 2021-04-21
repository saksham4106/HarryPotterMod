package com.saksham.pottercraftmod.world.biomes;

import com.saksham.pottercraftmod.init.BiomeFeaturesInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class ForbiddenForestBiome extends Biome{

	public ForbiddenForestBiome() {
		super(new Biome.Builder()
				.surfaceBuilder(BiomeFeaturesInit.FORBIDDEN_SURFACE_BUILDER, SurfaceBuilder.GRAVEL_CONFIG)
				.precipitation(RainType.RAIN)
				.category(Category.FOREST)
				.depth(0.08f)
				.scale(0.01f)
				.temperature(0.75f)
				.downfall(0.2f)
				.waterColor(4159204)
	            .waterFogColor(329011)
	            .parent(null));
		

		this.addStructure(Feature.MINESHAFT.withConfiguration(new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL)));
		this.addStructure(Feature.STRONGHOLD.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,BiomeFeaturesInit.FORBIDDEN_TREE.withConfiguration(DefaultBiomeFeatures.DARK_OAK_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(3, 0.4f, 1))) );
		DefaultBiomeFeatures.addOres(this);
	    DefaultBiomeFeatures.addCarvers(this);
	    DefaultBiomeFeatures.addStructures(this);
	    DefaultBiomeFeatures.addMonsterRooms(this);
	    this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.VINES.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
	    
	    this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE, 50, 4, 4));
	    this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 55, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.WITCH, 10, 1, 1));

	    
	    
	}

}
