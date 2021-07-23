package com.saksham.pottercraftmod.common.registry;

import com.saksham.pottercraftmod.PottercraftMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ConfiguredStructures {

    public static StructureFeature<?, ?> CONFIGURED_TEST = StructureInit.TEST.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    public static void registerConfiguredStructures(){
        // Register Configured Structures, can't use DR because it doesnt exist for CS
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(PottercraftMod.MOD_ID, "configured_test"), CONFIGURED_TEST);

        // This is done for our structure to work on Flat worlds
        FlatGenerationSettings.STRUCTURES.put(StructureInit.TEST.get(), CONFIGURED_TEST);
    }
}
