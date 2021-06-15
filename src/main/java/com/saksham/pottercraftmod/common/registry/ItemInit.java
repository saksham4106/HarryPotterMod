package com.saksham.pottercraftmod.common.registry;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.PottercraftMod.PottercraftItemGroup;
import com.saksham.pottercraftmod.common.items.FlooPowderItem;
import com.saksham.pottercraftmod.common.items.NameplateItem;
import com.saksham.pottercraftmod.common.items.SpellItem;
import com.saksham.pottercraftmod.common.items.TestItem;
import com.saksham.pottercraftmod.common.items.WandItem;
import com.saksham.pottercraftmod.common.items.hallows.InvisibilityCloak;
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
			() -> new WandItem(new Item.Properties().group(itemGroup).maxStackSize(1).maxDamage(100), 1.25f));

	public static final RegistryObject<Item> SPELL_BALL = ITEMS.register("spell_ball",
			() -> new SpellItem(new Item.Properties(), 2));



	public static final RegistryObject<Item> DRAGON_HEARTSTRING = ITEMS.register("dragon_heartstring",
			() -> new Item(new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> PHOENIX_FEATHER = ITEMS.register("phoenix_feather",
			() -> new Item(new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> UNICORN_TAIL = ITEMS.register("unicorn_tail",
			() -> new Item(new Item.Properties().group(itemGroup)));



	public static final RegistryObject<Item> FLOO_POWDER = ITEMS.register("floo_powder",
			() -> new FlooPowderItem(new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> NAMEPLATE_ITEM = ITEMS.register("nameplate_item",
			() -> new NameplateItem(new Item.Properties().group(itemGroup)));
	
	public static final RegistryObject<Item> TEST = ITEMS.register("test",
			() -> new TestItem(new Item.Properties().group(itemGroup)));
	
	
	public static final RegistryObject<Item> INVISIBILITY_CLOAK = ITEMS.register("invisibility_cloak",
			() -> new InvisibilityCloak(ModArmorMaterial.INVISIBILITY_CLOAK, EquipmentSlotType.HEAD,  new Item.Properties().group(itemGroup)));
	

	
	public static final RegistryObject<Item> GILLYWEED_ITEM = ITEMS.register("gillyweed",
			() -> new Item(new Item.Properties().group(itemGroup).food(new Food.Builder().hunger(1).saturation(1f)
					.effect(() -> new EffectInstance(Effects.WATER_BREATHING, 1200, 1), 1.0f).build())));
	
	public static final RegistryObject<Item> GRIFFYNDOR_BANNER_PATTERN = ITEMS.register("gryffindor_banner_pattern", 
			() -> new BannerPatternItem(BannerInit.registerPattern("gryffindor_flag_pattern", Items.STICK.getDefaultInstance()), new Item.Properties().maxStackSize(1)));
	
	public static final RegistryObject<Item> SLYTHERIN_BANNER_PATTERN = ITEMS.register("slytherin_banner_pattern", 
			() -> new BannerPatternItem(BannerInit.registerPattern("slytherin_flag", Items.APPLE.getDefaultInstance()), new Item.Properties().maxStackSize(1)));
	
	
	public static final RegistryObject<Item> RAVENCLAW_BANNER_PATTERN = ITEMS.register("ravenclaw_banner_pattern", 
			() -> new BannerPatternItem(BannerInit.registerPattern("ravenclaw_flag", Items.APPLE.getDefaultInstance()), new Item.Properties().maxStackSize(1)));
	
	
	public static final RegistryObject<Item> HUFFLEPUFF_BANNER_PATTERN = ITEMS.register("hufflepuff_banner_pattern", 
			() -> new BannerPatternItem(BannerInit.registerPattern("hufflepuff_flag", Items.APPLE.getDefaultInstance()), new Item.Properties().maxStackSize(1)));
	
	
	

}
