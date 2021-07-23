package com.saksham.pottercraftmod.core.events;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.common.items.hallows.ResurrectionStone;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobDieEvent {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void modDieEvent(LivingDeathEvent event){
        CompoundNBT nbt = event.getEntityLiving().serializeNBT();
        if(event.getEntity() instanceof LivingEntity) {
            if (!EntityTypeTags.getCollection().get(new ResourceLocation(PottercraftMod.MOD_ID, "non_resurrectable")).contains(event.getEntityLiving().getType())){
                if(event.getEntityLiving().getEntityString() != null) {
                    nbt.putString("id", event.getEntityLiving().getEntityString());

                    ResurrectionStone.entityNBT = nbt;
                    ResurrectionStone.entityHealth = event.getEntityLiving().getMaxHealth();
                }
            }else{
                ResurrectionStone.entityNBT = new CompoundNBT();
            }
        }
    }
}

