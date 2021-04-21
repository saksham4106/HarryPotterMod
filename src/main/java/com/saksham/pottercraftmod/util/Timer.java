package com.saksham.pottercraftmod.util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class Timer {
	
	private int counter = 0;
	private static boolean isAllowed;
	
	public Timer() {
		MinecraftForge.EVENT_BUS.register(this);
		isAllowed = true;
	}
	@SubscribeEvent
	public void clientTick(TickEvent event) {
		//System.out.println("tick");	
		counter ++;
		if(counter == 1000) {
			isAllowed = true;
			counter = 0;
			if(event.side == LogicalSide.SERVER)
			System.out.println("hahahahahaha");
		}
	}
	
	public boolean isAllowed(){
		System.out.println(isAllowed);
		return isAllowed;
	}
	
	public void setIsAllowed(boolean bool) {
		isAllowed = bool; 
	}
	
	

}
