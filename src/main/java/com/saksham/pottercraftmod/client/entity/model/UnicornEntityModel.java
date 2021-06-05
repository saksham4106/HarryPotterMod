package com.saksham.pottercraftmod.client.entity.model;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.common.entity.UnicornEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UnicornEntityModel extends AnimatedGeoModel<UnicornEntity>{
    @Override
    public ResourceLocation getModelLocation(UnicornEntity object)
    {
        return new ResourceLocation(PottercraftMod.MOD_ID, "geo/unicorn_entity.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(UnicornEntity object)
    {
        return new ResourceLocation(PottercraftMod.MOD_ID, "textures/entities/unicorn.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(UnicornEntity object)
    {
        return new ResourceLocation(PottercraftMod.MOD_ID, "animations/unicorn_entity.animation.json");
    }

}
