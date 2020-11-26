package com.saksham.pottercraftmod.util;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.client.entity.render.SpellEntityRender;
import com.saksham.pottercraftmod.init.KeyBindInit;
import com.saksham.pottercraftmod.init.ModEntityTypes;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		KeyBindInit.registerKeys();
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SPELL_ENTITY.get(), SpellEntityRender::new);

	}
}
