package com.saksham.pottercraftmod.core.network;

import com.saksham.pottercraftmod.PottercraftMod;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {

	private static SimpleChannel INSTANCE;
	private static int ID = 0;

	private static int nextID() {
		return ID++;
	}

	public static void registerMessages() {
		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(PottercraftMod.MOD_ID + "my_networking"),
				() -> "1.0", s -> true, s -> true);
		
		INSTANCE.messageBuilder(ChangeSpellPacket.class, nextID())
		.encoder(ChangeSpellPacket::toBytes)
		.decoder(ChangeSpellPacket::new)
		.consumer(ChangeSpellPacket::handle)
		.add();
		
		INSTANCE.messageBuilder(FlooStationPacket.class, nextID())
		.encoder(FlooStationPacket::toBytes)
		.decoder(FlooStationPacket::new)
		.consumer(FlooStationPacket::handle)
		.add();
	}

	public static void sendToServer(Object packet) {
		INSTANCE.sendToServer(packet);
	}
	
	public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }
}
