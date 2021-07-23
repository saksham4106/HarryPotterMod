package com.saksham.pottercraftmod.common.items.hallows;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.List;

public class InvisibilityCloak extends ArmorItem{
	private int counter;
	private boolean canSlowed;

	public InvisibilityCloak(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(counter > 200){
			counter = 0;
			canSlowed = true;
		}
		counter++;
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		player.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, 2, 2, true,
				false, true));
		if(canSlowed){
			player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 201, 2, false,
				false, true));
			canSlowed = false;
		}
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if(InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT)){
			tooltip.add(new StringTextComponent("Gives you slowness"));
		}else{
			tooltip.add(new StringTextComponent("Hold Shift for more information"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
