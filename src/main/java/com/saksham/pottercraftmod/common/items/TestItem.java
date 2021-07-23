package com.saksham.pottercraftmod.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

//TODO: Remove this
public class TestItem extends Item{

	public TestItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		System.out.println(ForgeHooks.getBurnTime(Items.COAL.getDefaultInstance()));
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	
}
