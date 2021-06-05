package com.saksham.pottercraftmod.common.tileentity;

import com.saksham.pottercraftmod.common.init.ModTileEntities;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class NameplateTileEntity extends SignTileEntity {
	public NameplateTileEntity() {
		super();
	}

	@Override
	public TileEntityType<?> getType() {
		return ModTileEntities.NAMEPLATE.get();
	}
}
