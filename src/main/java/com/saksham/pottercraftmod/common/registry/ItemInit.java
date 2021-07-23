package com.saksham.pottercraftmod.common.registry;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.PottercraftMod.PottercraftItemGroup;
import com.saksham.pottercraftmod.common.items.*;
import com.saksham.pottercraftmod.common.items.hallows.InvisibilityCloak;
import com.saksham.pottercraftmod.common.items.hallows.ResurrectionStone;
import com.saksham.pottercraftmod.core.util.ModArmorMaterial;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			PottercraftMod.MOD_ID);

	public final static PottercraftItemGroup itemGroup = PottercraftItemGroup.instance;

	public static final RegistryObject<Item> WAND = ITEMS.register("wand",
			() -> new SimpleWandItem(new Item.Properties().group(itemGroup).maxStackSize(1).maxDamage(100)));


	public static final RegistryObject<Item> DRAGON_HEARTSTRING = ITEMS.register("dragon_heartstring",
			() -> new Item(new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> PHOENIX_FEATHER = ITEMS.register("phoenix_feather",
			() -> new Item(new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> UNICORN_TAIL = ITEMS.register("unicorn_tail",
			() -> new Item(new Item.Properties().group(itemGroup)));



	public static final RegistryObject<Item> RESURRECTION_STONE = ITEMS.register("resurrection_stone",
			() -> new ResurrectionStone(new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> INVISIBILITY_CLOAK = ITEMS.register("invisibility_cloak",
			() -> new InvisibilityCloak(ModArmorMaterial.INVISIBILITY_CLOAK, EquipmentSlotType.HEAD,  new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> ELDER_WAND = ITEMS.register("elder_wand",
			() -> new ElderWandItem(new Item.Properties().group(itemGroup).maxStackSize(1).maxDamage(2048)));





	public static final RegistryObject<Item> FLOO_POWDER = ITEMS.register("floo_powder",
			() -> new FlooPowderItem(new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> NAMEPLATE_ITEM = ITEMS.register("nameplate_item",
			() -> new NameplateItem(new Item.Properties().group(itemGroup)));
	


	
	public static final RegistryObject<Item> GILLYWEED_ITEM = ITEMS.register("gillyweed",
			() -> new Item(new Item.Properties().group(itemGroup).food(new Food.Builder().hunger(1).saturation(1f)
					.effect(() -> new EffectInstance(Effects.WATER_BREATHING, 1200, 1), 1.0f).build())));






	public static final RegistryObject<Item> GRIFFYNDOR_BANNER_PATTERN = ITEMS.register("gryffindor_banner_pattern", 
			() -> new BannerPatternItem(BannerInit.registerPattern("gryffindor_flag_pattern"), new Item.Properties().maxStackSize(1)));
	
	public static final RegistryObject<Item> SLYTHERIN_BANNER_PATTERN = ITEMS.register("slytherin_banner_pattern", 
			() -> new BannerPatternItem(BannerInit.registerPattern("slytherin_flag"), new Item.Properties().maxStackSize(1)));

	public static final RegistryObject<Item> RAVENCLAW_BANNER_PATTERN = ITEMS.register("ravenclaw_banner_pattern", 
			() -> new BannerPatternItem(BannerInit.registerPattern("ravenclaw_flag"), new Item.Properties().maxStackSize(1)));

	public static final RegistryObject<Item> HUFFLEPUFF_BANNER_PATTERN = ITEMS.register("hufflepuff_banner_pattern", 
			() -> new BannerPatternItem(BannerInit.registerPattern("hufflepuff_flag"), new Item.Properties().maxStackSize(1)));


}
