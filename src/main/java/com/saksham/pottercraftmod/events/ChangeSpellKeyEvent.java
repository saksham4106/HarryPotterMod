package com.saksham.pottercraftmod.events;


import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.init.KeyBindInit;
import com.saksham.pottercraftmod.network.Networking;
import com.saksham.pottercraftmod.network.ChangeSpellPacket;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;


@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Bus.FORGE)
public class ChangeSpellKeyEvent {
	
	@SubscribeEvent
	public static void playerPressKeybindEvent(InputEvent event) {
		while(KeyBindInit.changeSpell.isPressed()) {
			Networking.sendToServer(new ChangeSpellPacket(1));
		}
	}
}
