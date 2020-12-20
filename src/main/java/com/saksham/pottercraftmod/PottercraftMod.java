package com.saksham.pottercraftmod;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

import com.saksham.pottercraftmod.init.BlockInit;
import com.saksham.pottercraftmod.init.ItemInit;
import com.saksham.pottercraftmod.init.ModEntityTypes;
import com.saksham.pottercraftmod.init.ModTileEntities;
import com.saksham.pottercraftmod.network.Networking;
import com.saksham.pottercraftmod.objects.blocks.FlooFireBlock;
import com.saksham.pottercraftmod.objects.blocks.NameplateBlock;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;


@Mod("pottercraftmod")
@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Bus.MOD)
public class PottercraftMod
{

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "pottercraftmod";
	public static PottercraftMod instance;

    public PottercraftMod() {
		instance = this;
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::doClientStuff);
		
		
		
		//ParticleInit.PARTICLE_TYPES.register(modEventBus);
		ItemInit.ITEMS.register(modEventBus);
		//RecipeSerializerInit.RECIPE_SERIALIZERS.register(modEventBus);
		//FluidInit.FLUIDS.register(modEventBus);
		BlockInit.BLOCKS.register(modEventBus);
		ModTileEntities.TILE_ENTITY_TYPES.register(modEventBus);
		//ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
		ModEntityTypes.ENTITY_TYPES.register(modEventBus);
		//BiomeInit.BIOMES.register(modEventBus);
		//DimensionInit.MOD_DIMENSIONS.register(modEventBus);
		
		
		instance = this;
		MinecraftForge.EVENT_BUS.register(this);

    }
    
    @SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		BlockInit.BLOCKS.getEntries().stream()
				.filter(block -> !(block.get() instanceof FlooFireBlock) && !(block.get() instanceof NameplateBlock))
				.map(RegistryObject::get).forEach(block -> {
					final Item.Properties properties = new Item.Properties().group(PottercraftItemGroup.instance);
					final BlockItem blockItem = new BlockItem(block, properties);
					blockItem.setRegistryName(block.getRegistryName());
					registry.register(blockItem);
				});

		LOGGER.debug("Registered BlockItems!");
		
	}


    private void setup(final FMLCommonSetupEvent event)
    {
    	Networking.registerMessages();
    }


    private void doClientStuff(final FMLClientSetupEvent event) {
       
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
     
    }
    
    
   	public static class PottercraftItemGroup extends ItemGroup {
   		public static final PottercraftItemGroup instance = new PottercraftItemGroup(ItemGroup.GROUPS.length, "pottercrafttab");

   		private PottercraftItemGroup(int index, String label) {
   			super(index, label);
   		}

		@Override
		public ItemStack createIcon() {
			// TODO Auto-generated method stub
			return ItemInit.WAND.get().getDefaultInstance();
		}
   	}
    
}
