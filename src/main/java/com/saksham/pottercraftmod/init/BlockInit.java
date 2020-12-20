package com.saksham.pottercraftmod.init;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.objects.blocks.FlooFireBaseBlock;
import com.saksham.pottercraftmod.objects.blocks.FlooFireBlock;
import com.saksham.pottercraftmod.objects.blocks.NameplateBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS,
			PottercraftMod.MOD_ID);
	
	
	public static final RegistryObject<Block> MINISTRY_OF_MAGIC_BRICK = BLOCKS.register("ministry_of_magic_brick",
			() -> new Block(Block.Properties.create(Material.ROCK)));
	
	
	public static final RegistryObject<Block> FLOO_FIRE = BLOCKS.register("floo_fire", 
			() -> new FlooFireBlock(Block.Properties.create(Material.FIRE)));
	
	
	public static final RegistryObject<Block> FLOO_FIRE_BLOCK = BLOCKS.register("floo_fire_block", 
			() -> new FlooFireBaseBlock(Block.Properties.create(Material.FIRE).tickRandomly()));
	
	public static final RegistryObject<Block> GOLD_NAMEPLATE = BLOCKS.register("gold_nameplate", 
			() -> new NameplateBlock(Block.Properties.create(Material.IRON)));
	
	
	
}
