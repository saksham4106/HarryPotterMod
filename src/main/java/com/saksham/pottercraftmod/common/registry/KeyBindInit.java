package com.saksham.pottercraftmod.common.registry;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindInit {
	
	public static final KeyBinding changeSpell = new KeyBinding("key.change_spell", GLFW.GLFW_KEY_P, "key.categories.misc");
	public static KeyBinding[] myKeys = ArrayUtils.addAll(new KeyBinding[] {KeyBindInit.changeSpell});
	public static void registerKeys() {

		for (KeyBinding myKey : myKeys) {
			ClientRegistry.registerKeyBinding(myKey);
		}
	}
}
