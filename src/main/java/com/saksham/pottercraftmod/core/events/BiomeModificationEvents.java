package com.saksham.pottercraftmod.core.events;

import com.mojang.serialization.Codec;
import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.common.registry.ConfiguredStructures;
import com.saksham.pottercraftmod.common.registry.StructureInit;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeModificationEvents {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void biomeModification(final BiomeLoadingEvent event){
        event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_TEST);
    }

    private static Method GETCODEC_METHOD;
    @SubscribeEvent
    public void addDimensionalSpacing(final WorldEvent.Load event){
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld) event.getWorld();
            RegistryKey<World> dimension = ObfuscationReflectionHelper.getPrivateValue(World.class, serverWorld, "field_73011_w");

            /* Skip Terraforged's chunk generation because they do the stuff for you*/
            try{
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class,
                        "func_230347_a_");
                ResourceLocation cgRl = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkProvider().generator));
            }catch(Exception e) {
                PottercraftMod.LOGGER.error("Was unable to check if " + dimension.getLocation() + " is using Terraforged's ChunkGenerator.");
            }

            if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator && dimension.equals(World.OVERWORLD)){
                return;
            }

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap.putIfAbsent(StructureInit.TEST.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.TEST.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
        }
    }
}
