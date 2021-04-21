package com.saksham.pottercraftmod.items.hallows;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

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

}
