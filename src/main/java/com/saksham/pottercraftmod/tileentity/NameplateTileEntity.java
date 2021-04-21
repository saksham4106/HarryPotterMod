package com.saksham.pottercraftmod.tileentity;

import javax.annotation.Nullable;

import com.saksham.pottercraftmod.init.ModTileEntities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.server.ServerWorld;

public class NameplateTileEntity extends SignTileEntity {
	public NameplateTileEntity() {
		super();
	}

	@Override
	public TileEntityType<?> getType() {
		return ModTileEntities.NAMEPLATE.get();
	}
}
