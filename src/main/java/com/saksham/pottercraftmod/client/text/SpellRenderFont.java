package com.saksham.pottercraftmod.client.text;

import com.saksham.pottercraftmod.init.ItemInit;
import com.saksham.pottercraftmod.objects.items.WandItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class SpellRenderFont {
	
	public static void render(RenderGameOverlayEvent.Post event) {
		ClientPlayerEntity player = Minecraft.getInstance().player;
		if(player.getHeldItemMainhand().getItem() == ItemInit.WAND.get()) {
			renderSpell();
			
		}
	}
	
	
	public static void renderSpell() {
		Minecraft.getInstance().fontRenderer.drawString("Current Spell: ", 75, 340, 0xeeeff3);
		Minecraft.getInstance().fontRenderer.drawString(WandItem.currentDisplaySpell, 150, 340, 0xeb6fde);
		
	}


}
