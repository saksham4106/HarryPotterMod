package com.saksham.pottercraftmod.common.registry;
//
//import com.saksham.pottercraftmod.container.TeleportingContainer;
//
//import net.minecraft.inventory.container.Container;
//import net.minecraft.inventory.container.ContainerType;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraftforge.event.RegistryEvent;
//import net.minecraftforge.fml.network.IContainerFactory;
//
//public class ModContainers {
//	public static ContainerType<TeleportingContainer> TELEPORTING_CONTAINER;
//	
//	public static void register(RegistryEvent<ContainerType<?>> event) {
//		
//		TELEPORTING_CONTAINER = createContainerType((windowId, inventory, buffer) -> {
//			CompoundNBT nbt = buffer.readCompoundTag();
//			return new TeleportingContainer(windowId, nbt == null ? new CompoundNBT() : nbt);
//		});
//	}
//	
//	 private static <T extends Container> ContainerType<T> createContainerType(IContainerFactory<T> factory) {
//	        return new ContainerType<T>(factory);
//	    }
//
//}
