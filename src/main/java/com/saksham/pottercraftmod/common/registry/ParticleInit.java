//package com.saksham.pottercraftmod.common.init;
//
//import com.mojang.serialization.Codec;
//import com.saksham.pottercraftmod.PottercraftMod;
//import com.saksham.pottercraftmod.common.particles.WandSpellParticle;
//import com.saksham.pottercraftmod.common.particles.WandSpellParticle.WandSpellParticleData;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.particles.ParticleType;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.RegistryObject;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//
//@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
//public class ParticleInit {
//	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(
//			ForgeRegistries.PARTICLE_TYPES, PottercraftMod.MOD_ID);
//
//	public static final RegistryObject<ParticleType<WandSpellParticleData>> WAND_SPELL_PARTICLE = PARTICLE_TYPES.register(
//			"wand_spell_particle",
//			() -> new ParticleType<WandSpellParticleData>(false, WandSpellParticleData.DESERIALIZER) {
//				@Override
//				public Codec<WandSpellParticleData> func_230522_e_() {
//					return null;
//				}
//			});
//
//
//	@SuppressWarnings("resource")
//	@SubscribeEvent
//	public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
//		Minecraft.getInstance().particles.registerFactory(ParticleInit.WAND_SPELL_PARTICLE.get(),
//				WandSpellParticle.Factory::new);
//	}
//
//
//
//}
