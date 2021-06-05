package com.saksham.pottercraftmod.common.init;

import com.saksham.pottercraftmod.core.world.biomes.ForbiddenForestBiome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeInit {
	
	@SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event)
    {
		registerBiome(new ForbiddenForestBiome(), "forbidden_forest", BiomeManager.BiomeType.COOL, BiomeDictionary.Type.DENSE, 2);
    }
	
	public static void registerBiome(Biome biome, String reg_name, BiomeManager.BiomeType biomeManagerType, BiomeDictionary.Type type, int weightIn) {
		biome.setRegistryName(reg_name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, type);
		BiomeManager.addSpawnBiome(biome);
		BiomeManager.addBiome(biomeManagerType, new BiomeManager.BiomeEntry(biome, weightIn));
		
	}
	


}
