package com.saksham.pottercraftmod.init;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.entity.SpellEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES,
			PottercraftMod.MOD_ID);
	
	public static final RegistryObject<EntityType<SpellEntity>> SPELL_ENTITY = ENTITY_TYPES
			.register("spell_entity",
					() -> EntityType.Builder.<SpellEntity>create(SpellEntity::new, EntityClassification.MISC)
							.size(0.9F, 1.3F)
							.build(new ResourceLocation(PottercraftMod.MOD_ID, "spell_entity").toString()));
}
