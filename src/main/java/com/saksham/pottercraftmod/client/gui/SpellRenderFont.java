package com.saksham.pottercraftmod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.saksham.pottercraftmod.common.init.ItemInit;
import com.saksham.pottercraftmod.common.items.WandItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class SpellRenderFont {

	public static void render(RenderGameOverlayEvent.Text event) {
		ClientPlayerEntity player = Minecraft.getInstance().player;
		if(player.getHeldItemMainhand().getItem() == ItemInit.WAND.get()) {
			renderSpell(event.getMatrixStack());
			
		}
	}

	public static void renderSpell(MatrixStack stack) {
		Minecraft.getInstance().fontRenderer.drawString(stack,"Current Spell: ", 75, 340, 0xeeeff3);
		Minecraft.getInstance().fontRenderer.drawString(stack, WandItem.currentDisplaySpell, 150, 340, 0xeb6fde);
		
	}


}
