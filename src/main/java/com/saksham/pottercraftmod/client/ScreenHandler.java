package com.saksham.pottercraftmod.client;

import com.saksham.pottercraftmod.client.gui.FlooTeleportScreen;
import com.saksham.pottercraftmod.client.gui.NameplateScreen;
import com.saksham.pottercraftmod.common.tileentity.NameplateTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class ScreenHandler {

    public static void displayFlooTeleportScreen(Entity entityIn, BlockPos pos){
        Minecraft.getInstance().displayGuiScreen(new FlooTeleportScreen((PlayerEntity) entityIn, pos));
    }

    public static void openNameplateScreen(NameplateTileEntity te) {
        Minecraft.getInstance().displayGuiScreen(new NameplateScreen(te));
    }
}
