package com.saksham.pottercraftmod.common.init;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.common.blocks.FlooFireBaseBlock;
import com.saksham.pottercraftmod.common.blocks.FlooFireBlock;
import com.saksham.pottercraftmod.common.blocks.NameplateBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GravelBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			PottercraftMod.MOD_ID);

	public static final RegistryObject<Block> MINISTRY_OF_MAGIC_BRICK = BLOCKS.register("ministry_of_magic_brick",
			() -> new Block(Block.Properties.create(Material.ROCK)));

	public static final RegistryObject<Block> FLOO_FIRE = BLOCKS.register("floo_fire",
			() -> new FlooFireBlock(Block.Properties.create(Material.FIRE)));

	public static final RegistryObject<Block> FLOO_FIRE_BLOCK = BLOCKS.register("floo_fire_block",
			() -> new FlooFireBaseBlock(Block.Properties.create(Material.FIRE).tickRandomly()));

	public static final RegistryObject<Block> GOLD_NAMEPLATE = BLOCKS.register("gold_nameplate",
			() -> new NameplateBlock(Block.Properties.create(Material.IRON).doesNotBlockMovement()
					.hardnessAndResistance(2.0f).sound(SoundType.METAL)));

	public static final RegistryObject<Block> ASH_GRAVEL = BLOCKS.register("ash_gravel",
			() -> new GravelBlock(Block.Properties.from(Blocks.GRAVEL)));

//	public static final RegistryObject<Block> FORBIDDEN_LOG = BLOCKS.register("forbidden_log",
//			() -> new Block(Block.Properties.from(Blocks.DARK_OAK_LOG)));
//
//	public static final RegistryObject<Block> FORBIDDEN_LEAVES = BLOCKS.register("forbidden_leaves",
//			() -> new Block(Block.Properties.from(Blocks.DARK_OAK_LEAVES)));

}
