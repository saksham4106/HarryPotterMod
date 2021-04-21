package com.saksham.pottercraftmod.world.biomes;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;
import com.saksham.pottercraftmod.init.BlockInit;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ForbiddenBiomeSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>{
	
	public static final BlockState DARK_ASH_BLOCK = BlockInit.ASH_GRAVEL.get().getDefaultState();
	public static final SurfaceBuilderConfig DARK_ASH_CONFIG = new SurfaceBuilderConfig(DARK_ASH_BLOCK, DARK_ASH_BLOCK, DARK_ASH_BLOCK);

	public ForbiddenBiomeSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> configFactory) {
		super(configFactory);

	}
	
	

	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise,
			BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
		
		if(noise > 2.2D) {
	        SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.GRAVEL_CONFIG);


		}else {
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, DARK_ASH_CONFIG);

		}
		
        
	}
	

}
