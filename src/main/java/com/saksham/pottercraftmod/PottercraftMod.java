package com.saksham.pottercraftmod;

import com.mojang.serialization.Codec;
import com.saksham.pottercraftmod.common.registry.*;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.saksham.pottercraftmod.common.blocks.FlooFireBlock;
import com.saksham.pottercraftmod.common.blocks.NameplateBlock;
import com.saksham.pottercraftmod.core.network.Networking;

import net.minecraft.client.renderer.Atlases;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Mod("pottercraftmod")
@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Bus.MOD)
public class PottercraftMod
{

	public static final Logger LOGGER = LogManager.getLogger();
	

	public static final String MOD_ID = "pottercraftmod";
	public static PottercraftMod instance;

    public PottercraftMod() {
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
		final IForgeRegistry<Item> registry = event.getRegistry();

		BlockInit.BLOCKS.getEntries().stream()
				.filter(block -> !(block.get() instanceof FlooFireBlock) && !(block.get() instanceof NameplateBlock))
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

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void biomeModification(final BiomeLoadingEvent event){
    	event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_TEST);
	}

	private static Method GETCODEC_METHOD;
    @SubscribeEvent
   	public void addDimensionalSpacing(final WorldEvent.Load event){
    	if(event.getWorld() instanceof ServerWorld){
    		ServerWorld serverWorld = (ServerWorld) event.getWorld();

			RegistryKey<World> dimension = ObfuscationReflectionHelper.getPrivateValue(World.class, serverWorld, "field_73011_w");

    		/* Skip Terraforged's chunk generation because they do the stuff for you*/
			try{
				if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class,
						"func_230347_a_");
				ResourceLocation cgRl = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkProvider().generator));
			}catch(Exception e) {

				PottercraftMod.LOGGER.error("Was unable to check if " + dimension.getLocation() + " is using Terraforged's ChunkGenerator.");
			}

			// Remove Structure from Superflat worlds
			if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator && dimension.equals(World.OVERWORLD)){

				return;
			}

			Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
			tempMap.putIfAbsent(StructureInit.TEST.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.TEST.get()));
			serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;

		}
	}
    
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
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
