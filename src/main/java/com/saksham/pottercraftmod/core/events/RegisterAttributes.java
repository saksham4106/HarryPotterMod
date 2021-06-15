package com.saksham.pottercraftmod.core.events;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.common.entity.OwlEntity;
import com.saksham.pottercraftmod.common.entity.UnicornEntity;
import com.saksham.pottercraftmod.common.registry.ModEntityTypes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegisterAttributes {

    @SubscribeEvent
    public static void registerAttribute(EntityAttributeCreationEvent event){
        event.put(ModEntityTypes.OWL_ENTITY.get(), OwlEntity.createAttributes().create());
        event.put(ModEntityTypes.UNICORN_ENTITY.get(), UnicornEntity.createAttributes().create());
    }
}
