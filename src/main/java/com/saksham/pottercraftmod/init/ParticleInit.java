package com.saksham.pottercraftmod.init;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.particles.WandSpellParticle;
import com.saksham.pottercraftmod.particles.WandSpellParticle.WandSpellParticleData;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ParticleInit {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = new DeferredRegister<>(
			ForgeRegistries.PARTICLE_TYPES, PottercraftMod.MOD_ID);

	public static final RegistryObject<ParticleType<WandSpellParticleData>> WAND_SPELL_PARTICLE = PARTICLE_TYPES.register(
			"wand_spell_particle",
			() -> new ParticleType<WandSpellParticleData>(false, WandSpellParticleData.DESERIALIZER));

	
	@SubscribeEvent
	public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particles.registerFactory(ParticleInit.WAND_SPELL_PARTICLE.get(),
				WandSpellParticle.Factory::new);
	}

}
