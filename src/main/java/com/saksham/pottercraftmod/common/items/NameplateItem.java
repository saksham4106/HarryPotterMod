package com.saksham.pottercraftmod.common.items;

import com.saksham.pottercraftmod.client.ScreenHandler;
import com.saksham.pottercraftmod.common.blocks.NameplateBlock;
import com.saksham.pottercraftmod.client.gui.NameplateScreen;
import com.saksham.pottercraftmod.common.registry.BlockInit;
import com.saksham.pottercraftmod.common.tileentity.NameplateTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class NameplateItem extends Item {
	public NameplateItem(Properties properties) {
		super(properties);
	}

	@Override
	@Nonnull
	public ActionResultType onItemUse(ItemUseContext context) {
		Direction side = context.getFace();
		PlayerEntity player = context.getPlayer();
		BlockPos pos = context.getPos();
		ItemStack stack = context.getItem();
		World world = context.getWorld();
		BlockPos newpos = pos.offset(side);

		if ((side == Direction.DOWN || side == Direction.UP) || !player.canPlayerEdit(pos, side, stack)) {
			return ActionResultType.FAIL;
		}

		world.setBlockState(newpos, BlockInit.GOLD_NAMEPLATE.get().getDefaultState().with(NameplateBlock.FACING, side), 3);
		stack.shrink(-1);
		NameplateTileEntity nameplateTileEntity = (NameplateTileEntity) world.getTileEntity(newpos);
		if (nameplateTileEntity != null && world.isRemote) {
			ScreenHandler.openNameplateScreen(nameplateTileEntity);
		}
		return ActionResultType.SUCCESS;
	}

}