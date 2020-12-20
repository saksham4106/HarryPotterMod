package com.saksham.pottercraftmod.init;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.objects.tileentity.NameplateTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
	
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(
			ForgeRegistries.TILE_ENTITIES, PottercraftMod.MOD_ID);
	

	public static final RegistryObject<TileEntityType<NameplateTileEntity>> NAMEPLATE = TILE_ENTITY_TYPES
			.register("nameplate", () -> TileEntityType.Builder
					.create(NameplateTileEntity::new, BlockInit.GOLD_NAMEPLATE.get()).build(null));

}
