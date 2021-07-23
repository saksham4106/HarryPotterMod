package com.saksham.pottercraftmod;

import com.saksham.pottercraftmod.common.registry.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.saksham.pottercraftmod.core.setup.Config;
import com.saksham.pottercraftmod.common.blocks.FlooFireBlock;
import com.saksham.pottercraftmod.common.blocks.NameplateBlock;
import com.saksham.pottercraftmod.core.network.Networking;

import net.minecraft.client.renderer.Atlases;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.block.Block;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Mod("pottercraftmod")
@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Bus.MOD)
public class PottercraftMod {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "pottercraftmod";
	public static PottercraftMod instance;

    public PottercraftMod() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
    	GeckoLibMod.DISABLE_IN_DEV = true;
    	GeckoLib.initialize();
		instance = this;
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);


		ItemInit.ITEMS.register(modEventBus );
		//RecipeSerializerInit.RECIPE_SERIALIZERS.register(modEventBus);
		//FluidInit.FLUIDS.register(modEventBus);
		BlockInit.BLOCKS.register(modEventBus);
		ModTileEntities.TILE_ENTITY_TYPES.register(modEventBus);
		//ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
		ModEntityTypes.ENTITY_TYPES.register(modEventBus);
		StructureInit.structures.register(modEventBus);
		//BiomeInit.BIOMES.register(modEventBus);
		//DimensionInit.MOD_DIMENSIONS.register(modEventBus);
		
		instance = this;
		MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		List<Class<? extends Block>> blacklistedBlocks = new ArrayList<>();
		Collections.addAll(blacklistedBlocks, FlooFireBlock.class, NameplateBlock.class);

		final IForgeRegistry<Item> registry = event.getRegistry();
		BlockInit.BLOCKS.getEntries().stream()
				.filter(block -> blacklistedBlocks.contains(block.get().getClass()))
				.map(RegistryObject::get).forEach(block -> {
					final Item.Properties properties = new Item.Properties().group(PottercraftItemGroup.instance);
					final BlockItem blockItem = new BlockItem(block, properties);
					blockItem.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
					registry.register(blockItem);
				});
		LOGGER.debug("Registered BlockItems!");
	}

    private void setup(final FMLCommonSetupEvent event){
    	Networking.registerMessages();
    	event.enqueueWork(StructureInit::setupStructures);
    }

    @SubscribeEvent
    public static void stitchTextures(TextureStitchEvent.Pre event) {
    	if(event.getMap().getTextureLocation().equals(Atlases.SIGN_ATLAS)) {
    		event.addSprite(new ResourceLocation(MOD_ID, "entities/nameplate"));
    	}
    }

   	public static class PottercraftItemGroup extends ItemGroup {
   		public static final PottercraftItemGroup instance = new PottercraftItemGroup(ItemGroup.GROUPS.length, "pottercrafttab");

   		private PottercraftItemGroup(int index, String label) {
   			super(index, label);
   		}

		@Override
		public ItemStack createIcon() {
			return ItemInit.WAND.get().getDefaultInstance();
		}
   	}
}