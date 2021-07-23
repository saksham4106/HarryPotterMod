package com.saksham.pottercraftmod.common.items.hallows;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.UUID;

public class ResurrectionStone extends Item {
    public static CompoundNBT entityNBT;
    public static float entityHealth;
    public ResurrectionStone(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(entityNBT != null){
            entityNBT.putFloat("Health", entityHealth);
            entityNBT.putString("UUID", UUID.randomUUID().toString());
            Vector3d vec = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY).getHitVec();

            Entity entity = EntityType.loadEntityAndExecute(entityNBT, worldIn, (entityIn) -> {
                entityIn.setPositionAndRotation(vec.x,vec.y, vec.z,
                        playerIn.rotationYaw, playerIn.rotationPitch);
                return entityIn;
            });

            if(entity != null && !worldIn.isRemote){
                worldIn.addEntity(entity);
            }
            entityNBT = null;
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
