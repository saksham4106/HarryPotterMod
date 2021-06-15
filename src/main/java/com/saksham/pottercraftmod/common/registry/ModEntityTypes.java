package com.saksham.pottercraftmod.common.registry;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.common.entity.OwlEntity;
import com.saksham.pottercraftmod.common.entity.SpellEntity;
import com.saksham.pottercraftmod.common.entity.UnicornEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			PottercraftMod.MOD_ID);
	
	public static final RegistryObject<EntityType<SpellEntity>> SPELL_ENTITY = ENTITY_TYPES
			.register("spell_entity",
					() -> EntityType.Builder.<SpellEntity>create(SpellEntity::new, EntityClassification.MISC)
							.size(0.9F, 1.3F)
							.build(new ResourceLocation(PottercraftMod.MOD_ID, "spell_entity").toString()));
	
	public static final RegistryObject<EntityType<OwlEntity>> OWL_ENTITY = ENTITY_TYPES
			.register("owl_entity",
					() -> EntityType.Builder.<OwlEntity>create(OwlEntity::new, EntityClassification.CREATURE)
							.size(0.9f, 1.3f)
							.build(new ResourceLocation(PottercraftMod.MOD_ID, "owl_entity").toString()));

	public static final RegistryObject<EntityType<UnicornEntity>> UNICORN_ENTITY = ENTITY_TYPES
			.register("unicorn_entity",
					() -> EntityType.Builder.<UnicornEntity>create(UnicornEntity::new, EntityClassification.CREATURE)
						.size(0.9f, 1.3f)
						.build(new ResourceLocation(PottercraftMod.MOD_ID, "unicorn_entity").toString()));
}
