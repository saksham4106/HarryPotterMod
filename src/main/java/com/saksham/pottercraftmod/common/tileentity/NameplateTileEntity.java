package com.saksham.pottercraftmod.common.tileentity;

import com.saksham.pottercraftmod.common.registry.ModTileEntities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;
import javax.annotation.Nullable;

public class NameplateTileEntity extends SignTileEntity {
	public NameplateTileEntity() {
		super();
	}

	@Override
	public TileEntityType<?> getType() {
		return ModTileEntities.NAMEPLATE.get();
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.pos, 0 ,getUpdateTag());
	}


	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbt = new CompoundNBT();
		this.write(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		this.read(state, tag);
	}
}
