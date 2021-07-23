package com.saksham.pottercraftmod.common.registry;

import com.saksham.pottercraftmod.PottercraftMod;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;

public class BannerInit {
	
	public static BannerPattern registerPattern (String id) {
		final ResourceLocation regId = new ResourceLocation(PottercraftMod.MOD_ID, id);
		final String snakeName = regId.toString().replace(':', '_');
		return BannerPattern.create(snakeName.toUpperCase(), snakeName, snakeName, false);
	}
}
