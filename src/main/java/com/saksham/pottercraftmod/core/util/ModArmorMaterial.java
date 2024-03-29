package com.saksham.pottercraftmod.core.util;

import java.util.function.Supplier;

import com.saksham.pottercraftmod.common.registry.ItemInit;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum ModArmorMaterial implements IArmorMaterial{
	
	INVISIBILITY_CLOAK("invisibility", 2, new int[] {1,2,1,2} , 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, () -> {return Ingredient.fromItems(ItemInit.INVISIBILITY_CLOAK.get());});

	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final LazyValue<Ingredient> repairMaterial;

	ModArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float toughnessIn, Supplier<Ingredient> repairMaterialSupplier) {
	  this.name = nameIn;
	  this.maxDamageFactor = maxDamageFactorIn;
	  this.damageReductionAmountArray = damageReductionAmountsIn;
	  this.enchantability = enchantabilityIn;
	  this.soundEvent = equipSoundIn;
	  this.toughness = toughnessIn;
	  this.repairMaterial = new LazyValue<>(repairMaterialSupplier);
	}

	public int getDurability(EquipmentSlotType slotIn) {
	  return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
	  return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	public int getEnchantability() {
	  return this.enchantability;
	}

	public SoundEvent getSoundEvent() {
	  return this.soundEvent;
	}

	public Ingredient getRepairMaterial() {
	  return this.repairMaterial.getValue();
	}

	@OnlyIn(Dist.CLIENT)
	public String getName() {
	  return this.name;
	}

	public float getToughness() {
	  return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return 0;
	}

}
