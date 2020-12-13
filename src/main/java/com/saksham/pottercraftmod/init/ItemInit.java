package com.saksham.pottercraftmod.init;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.PottercraftMod.PottercraftItemGroup;
import com.saksham.pottercraftmod.objects.items.FlooPowderItem;
import com.saksham.pottercraftmod.objects.items.SpellItem;
import com.saksham.pottercraftmod.objects.items.WandItem;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS,
			PottercraftMod.MOD_ID);

	public static final RegistryObject<Item> WAND = ITEMS.register("wand",
			() -> new WandItem(new Item.Properties().group(PottercraftItemGroup.instance).maxStackSize(1)));
	
	public static final RegistryObject<Item> SPELL_BALL = ITEMS.register("spell_ball",
			() -> new SpellItem(new Item.Properties(), 2));
	
	
	
	public static final RegistryObject<Item> DRAGON_HEARTSTRING = ITEMS.register("dragon_heartstring",
			() -> new Item(new Item.Properties().group(PottercraftItemGroup.instance)));
	
	public static final RegistryObject<Item> PHOENIX_FEATHER = ITEMS.register("phoenix_feather",
			() -> new Item(new Item.Properties().group(PottercraftItemGroup.instance)));
	
	public static final RegistryObject<Item> UNICORN_TAIL = ITEMS.register("unicorn_tail",
			() -> new Item(new Item.Properties().group(PottercraftItemGroup.instance)));
	
	
	
	public static final RegistryObject<Item> BLACK_WALNUT_LOG = ITEMS.register("black_walnut_log",
			() -> new Item(new Item.Properties().group(PottercraftItemGroup.instance)));
	
	public static final RegistryObject<Item> HOLLY_LOG = ITEMS.register("holly_log",
			() -> new Item(new Item.Properties().group(PottercraftItemGroup.instance)));
	
	
	
	public static final RegistryObject<Item> FLOO_POWDER = ITEMS.register("floo_powder", 
			() -> new FlooPowderItem(new Item.Properties().group(PottercraftItemGroup.instance)));
	
	
}
