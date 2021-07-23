//package com.saksham.pottercraftmod.common.registry;
//
//import com.saksham.pottercraftmod.PottercraftMod;
//
//
//import com.saksham.pottercraftmod.common.particles.type.EntityParticleType;
//import net.minecraft.client.Minecraft;
//import net.minecraft.particles.ParticleType;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//
//@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
//public class ParticleInit {
//    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(
//            ForgeRegistries.PARTICLE_TYPES, PottercraftMod.MOD_ID
//    );
//    
//    public static final RegistryObject<ParticleType<EntityParticleData>> ENTITY_PARTICLES = PARTICLE_TYPES.register(
//            "entity_particle", () -> new EntityParticleType()
//    );
//
//    @SubscribeEvent
//    public static void registerParticles(ParticleFactoryRegisterEvent event){
//        Minecraft.getInstance().particles.registerFactory(ENTITY_PARTICLES.get(), EntityParticleFactory::new);
//
//    }
//
//
//
//
//}
