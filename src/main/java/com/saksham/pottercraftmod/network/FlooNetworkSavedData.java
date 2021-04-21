package com.saksham.pottercraftmod.network;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.saksham.pottercraftmod.PottercraftMod;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class FlooNetworkSavedData extends WorldSavedData {

	private static final String DATA_NAME = PottercraftMod.MOD_ID;
	public final ConcurrentHashMap<String, BlockPos> data = new ConcurrentHashMap<>();

	public FlooNetworkSavedData() {
		super(DATA_NAME);

	}

	@Override
	public void read(CompoundNBT nbt) {
		ListNBT tagList = nbt.getList(DATA_NAME, Constants.NBT.TAG_COMPOUND);
		for (INBT tag : tagList) {
			CompoundNBT compound = (CompoundNBT) tag;
			BlockPos pos = NBTUtil.readBlockPos(compound.getCompound("BlockPos"));
			this.data.put(compound.getString("Name"), pos);
		}

	}

	@Override
	public CompoundNBT write(CompoundNBT tagCompound) {
		ListNBT tagList = new ListNBT();
		for (String name : data.keySet()) {
			CompoundNBT compound = new CompoundNBT();
			compound.putString("Name", name);
			compound.put("BlockPos", NBTUtil.writeBlockPos(data.get(name)));
			tagList.add(compound);

		}
		tagCompound.put(DATA_NAME, tagList);
		return tagCompound;
	}
	
	public static FlooNetworkSavedData get(World world) {
		MinecraftServer server = world.getServer();
		if (server != null) {
			ServerWorld serverWorld = server.getWorld(world.dimension.getType());
			DimensionSavedDataManager storage = serverWorld.getSavedData();
			return storage.getOrCreate(FlooNetworkSavedData::new, DATA_NAME);
		}

		return new FlooNetworkSavedData();

	}

	public void addFlooStation(String name, BlockPos pos) {
		data.put(name, pos);
		PottercraftMod.LOGGER.info("FlooStation Registered at " + pos.toString() + " Name: " + name);
		markDirty();
	}

	public void removeFlooStation(BlockPos pos) {
		boolean removedPlace = false;
		
		Iterator<String> i = this.data.keySet().iterator();
		
		while (i.hasNext() && !removedPlace) {
			String nextPlaceName = (String) i.next();
			if (this.data.get(nextPlaceName).equals(pos)) {
				PottercraftMod.LOGGER.info(
						"Removed FlooStation at (" + pos.toString() + "). Name: " + nextPlaceName);
				this.data.remove(nextPlaceName);
				removedPlace = true;
			}
		}
		if (!removedPlace) {
			PottercraftMod.LOGGER.warn("Failed to remove FlooStation at (" + pos.toString() + ").");

		}
		markDirty();
	}
	
	public BlockPos getPosFromName(String name){
		for(String names: data.keySet()) {
			System.out.println(names + "     " + name);
			if(names.equals(name)) {
				
				return this.data.get(name);
			}
		}
		
		return null; 
	}
	
	public Optional<String> getNameFromPos(BlockPos pos){
		return data.keySet().stream().filter(it -> it.equals(pos)).findFirst();
	}

}
