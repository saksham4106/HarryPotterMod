package com.saksham.pottercraftmod.common.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.core.world.structures.TestStructure;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class StructureInit {
    public static final DeferredRegister<Structure<?>> structures =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, PottercraftMod.MOD_ID);

    public static final RegistryObject<Structure<NoFeatureConfig>> TEST = structures.register("test_structure",
            () -> new TestStructure(NoFeatureConfig.CODEC));

    public static void setupStructures(){
       setupMapSpacingAndLand(TEST.get(),
                new StructureSeparationSettings(10,
                        5,
                        248142523),
                        true);
    }

    public static <T extends Structure<?>> void setupMapSpacingAndLand(T structure, StructureSeparationSettings structureSeparationSettings,
                                                                       boolean transformSurroundingLand){
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        if(transformSurroundingLand) {
            Structure.field_236384_t_ = ImmutableList.<Structure<?>>builder()
                    .addAll(Structure.field_236384_t_)
                    .add(structure)
                    .build();
        }

        DimensionStructuresSettings.field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                .putAll(DimensionStructuresSettings.field_236191_b_)
                .put(structure, structureSeparationSettings)
                .build();

        WorldGenRegistries.NOISE_SETTINGS.getEntries().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().getStructures().func_236195_a_();
            if(structureMap instanceof  ImmutableMap){
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureSeparationSettings);
                settings.getValue().getStructures().field_236193_d_ = tempMap;
            }else{
                structureMap.put(structure, structureSeparationSettings);
            }
        });

    }

}
