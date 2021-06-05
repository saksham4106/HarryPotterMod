package com.saksham.pottercraftmod.core.events;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.common.init.BlockInit;
import com.saksham.pottercraftmod.core.network.FlooNetworkSavedData;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PottercraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockBreak {

    @SubscribeEvent
    public static void nameplateBreakEvent(BlockEvent.BreakEvent event){
        event.getWorld().getWorld().setBlockState(event.getPos().up(), Blocks.ACACIA_LOG.getDefaultState());
        PottercraftMod.LOGGER.info("ok");
        System.out.println("ok");
        if(event.getState().getBlock() == BlockInit.GOLD_NAMEPLATE.get()){
            System.out.println("block broken");
            BlockPos pos = event.getPos();
            World worldIn = event.getWorld().getWorld();
            pos = pos.down(3);
            if(FlooNetworkSavedData.get(worldIn).getNameFromPos(pos) != null){
                FlooNetworkSavedData.get(worldIn).removeFlooStation(pos);
            }
        }
    }
}
