package com.saksham.pottercraftmod.core.network;

import java.util.function.Supplier;

import com.saksham.pottercraftmod.common.items.WandItem;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ChangeSpellPacket {
	 private final int id;
	    
	    
	    

	    public ChangeSpellPacket(PacketBuffer buf) {
	        id = buf.readInt();
	       
	        
	    }

	    public ChangeSpellPacket(int id) {
	        this.id = id;
	        
	       
	    }

	    public void toBytes(PacketBuffer buf) {
	        buf.writeInt(id);
	       
	        
	    }


	    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
	        ctx.get().enqueueWork(() -> {
	            ServerPlayerEntity playerIn = ctx.get().getSender();
	            if(playerIn.getHeldItemMainhand().getItem() instanceof WandItem) {
	            	playerIn.setHealth(1);
	            	int currentSpellId = WandItem.getCurrentSpellId();
	            	if (currentSpellId > WandItem.spellList.length - 2) {
	            		currentSpellId = 0;
	            	}else {
	            		currentSpellId++;
	            	}

	            	WandItem.setCurrentSpellId(currentSpellId);
	            	
	            }
	        });
	        return true;
	    }
}
