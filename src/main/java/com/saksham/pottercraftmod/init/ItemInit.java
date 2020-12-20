package com.saksham.pottercraftmod.init;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.PottercraftMod.PottercraftItemGroup;
import com.saksham.pottercraftmod.objects.items.FlooPowderItem;
import com.saksham.pottercraftmod.objects.items.NameplateItem;
import com.saksham.pottercraftmod.objects.items.SpellItem;
import com.saksham.pottercraftmod.objects.items.WandItem;

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
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS,
			PottercraftMod.MOD_ID);

	public final static PottercraftItemGroup itemGroup = PottercraftItemGroup.instance;

	public static final RegistryObject<Item> WAND = ITEMS.register("wand",
			() -> new WandItem(new Item.Properties().group(itemGroup).maxStackSize(1)));

	public static final RegistryObject<Item> SPELL_BALL = ITEMS.register("spell_ball",
			() -> new SpellItem(new Item.Properties(), 2));
	
	

	public static final RegistryObject<Item> DRAGON_HEARTSTRING = ITEMS.register("dragon_heartstring",
			() -> new Item(new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> PHOENIX_FEATHER = ITEMS.register("phoenix_feather",
			() -> new Item(new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> UNICORN_TAIL = ITEMS.register("unicorn_tail",
			() -> new Item(new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> BLACK_WALNUT_LOG = ITEMS.register("black_walnut_log",
			() -> new Item(new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> HOLLY_LOG = ITEMS.register("holly_log",
			() -> new Item(new Item.Properties().group(itemGroup)));

	
	
	public static final RegistryObject<Item> FLOO_POWDER = ITEMS.register("floo_powder",
			() -> new FlooPowderItem(new Item.Properties().group(itemGroup)));

	public static final RegistryObject<Item> NAMEPLATE_ITEM = ITEMS.register("nameplate_sign",
			() -> new NameplateItem(new Item.Properties().group(itemGroup), BlockInit.GOLD_NAMEPLATE.get()));

	
	@SuppressWarnings("deprecation")
	public static final RegistryObject<Item> GILLYWEED_ITEM = ITEMS.register("gillyweed",
			() -> new Item(new Item.Properties().group(itemGroup).food(new Food.Builder().hunger(1).saturation(1f)
					.effect(new EffectInstance(Effects.WATER_BREATHING, 1200, 1), 1.0f).build())));
	
	public static final RegistryObject<Item> GRIFFYNDOR_BANNER_PATTERN = ITEMS.register("gryffindor_banner_pattern", 
			() -> new BannerPatternItem(BannerInit.registerPattern("gryffindor_flag_pattern", Items.STICK.getDefaultInstance()), new Item.Properties().maxStackSize(1)));
	
	public static final RegistryObject<Item> SLYTHERIN_BANNER_PATTERN = ITEMS.register("slytherin_banner_pattern", 
			() -> new BannerPatternItem(BannerInit.registerPattern("slytherin_flag", Items.APPLE.getDefaultInstance()), new Item.Properties().maxStackSize(1)));
	
	
	public static final RegistryObject<Item> RAVENCLAW_BANNER_PATTERN = ITEMS.register("ravenclaw_banner_pattern", 
			() -> new BannerPatternItem(BannerInit.registerPattern("ravenclaw_flag", Items.APPLE.getDefaultInstance()), new Item.Properties().maxStackSize(1)));
	
	
	public static final RegistryObject<Item> HUFFLEPUFF_BANNER_PATTERN = ITEMS.register("hufflepuff_banner_pattern", 
			() -> new BannerPatternItem(BannerInit.registerPattern("hufflepuff_flag", Items.APPLE.getDefaultInstance()), new Item.Properties().maxStackSize(1)));
	
	
	

}
