package com.saksham.pottercraftmod;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.client.entity.render.OwlEntityRenderer;
import com.saksham.pottercraftmod.client.entity.render.SpellEntityRender;
import com.saksham.pottercraftmod.client.entity.render.UnicornEntityRenderer;
import com.saksham.pottercraftmod.client.text.SpellRenderFont;
import com.saksham.pottercraftmod.init.BlockInit;
import com.saksham.pottercraftmod.init.KeyBindInit;
import com.saksham.pottercraftmod.init.ModEntityTypes;
import com.saksham.pottercraftmod.init.ModTileEntities;
import com.saksham.pottercraftmod.network.ChangeSpellPacket;
import com.saksham.pottercraftmod.network.Networking;
import com.saksham.pottercraftmod.tileentity.tileentityrenderer.NameplaterRenderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		
		KeyBindInit.registerKeys();
		MinecraftForge.EVENT_BUS.addListener(SpellRenderFont::render);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SPELL_ENTITY.get(), SpellEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.OWL_ENTITY.get(), OwlEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.UNICORN_ENTITY.get(), UnicornEntityRenderer::new);
		RenderTypeLookup.setRenderLayer(BlockInit.FLOO_FIRE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInit.FLOO_FIRE_BLOCK.get(), RenderType.getCutout());
		ClientRegistry.bindTileEntityRenderer(ModTileEntities.NAMEPLATE.get(), NameplaterRenderer::new);
		keybindingListener(event);
		
		
	}
	
	public static void keybindingListener(FMLClientSetupEvent event) {
		if (KeyBindInit.changeSpell.isPressed()) {
			Networking.sendToServer(new ChangeSpellPacket(1));
		}
	}
	
	
}
