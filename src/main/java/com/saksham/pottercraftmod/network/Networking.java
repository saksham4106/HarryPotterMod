package com.saksham.pottercraftmod.network;

import com.saksham.pottercraftmod.PottercraftMod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {

	 private static SimpleChannel INSTANCE;
	 private static int ID = 0;

	 private static int nextID() {
	       return ID++;
	 }

    public static void registerMessages() {
    	INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(PottercraftMod.MOD_ID + "my_networking"), () -> "1.0",
                s -> true,
                s -> true);
    	
    	
    	INSTANCE.messageBuilder(ChangeSpellPacket.class, nextID())
        .encoder(ChangeSpellPacket::toBytes)
        .decoder(ChangeSpellPacket::new)
        .consumer(ChangeSpellPacket::handle)
        .add();
    }	
    
    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
