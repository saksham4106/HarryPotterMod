package com.saksham.pottercraftmod.core.network;

import java.util.function.Supplier;

import com.saksham.pottercraftmod.core.util.FlooStationValidator;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class FlooStationPacket {
	private final int id;
	private BlockPos pos;
	private final String flooStationName;

	public FlooStationPacket(PacketBuffer buf) {
		id = buf.readInt();
		pos = buf.readBlockPos();
		flooStationName = buf.readString();
	}

	public FlooStationPacket(int id, BlockPos pos, String flooStationName) {
		this.id = id;
		this.pos = pos;
		this.flooStationName = flooStationName;

	}

	public void toBytes(PacketBuffer buf) {
		buf.writeInt(id);
		buf.writeBlockPos(pos);
		buf.writeString(flooStationName);
	}

	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			World world = player.getEntityWorld();
			if (id == 1) {
				pos = pos.down(3);
				if (FlooStationValidator.isFlooStationValid(pos.south(3), world)) {
					FlooNetworkSavedData.get(player.getEntityWorld()).addFlooStation(flooStationName, pos.south(3));
				} else if (FlooStationValidator.isFlooStationValid(pos.north(3), world)) {
					FlooNetworkSavedData.get(player.getEntityWorld()).addFlooStation(flooStationName, pos.north(3));
				} else if (FlooStationValidator.isFlooStationValid(pos.west(3), world)) {
					FlooNetworkSavedData.get(player.getEntityWorld()).addFlooStation(flooStationName, pos.west(3));
				} else if (FlooStationValidator.isFlooStationValid(pos.east(3), world)) {
					FlooNetworkSavedData.get(player.getEntityWorld()).addFlooStation(flooStationName, pos.east(3));
				}
			} else if (id == 2) {
				if(FlooStationValidator.isFlooStationValid(pos.down(), world)){
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					world.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
					BlockPos pos2 = FlooNetworkSavedData.get(world).getPosFromName(flooStationName);
					player.attemptTeleport(pos2.getX(), pos2.getY(), pos2.getZ(), false);
				}
			}
		});
		return true;
	}
}
