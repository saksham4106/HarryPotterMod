package com.saksham.pottercraftmod.client.entity.model;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.entity.OwlEntity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;



public class OwlEntityModel extends AnimatedGeoModel<OwlEntity> {

    @Override
    public ResourceLocation getModelLocation(OwlEntity object)
    {
        return new ResourceLocation(PottercraftMod.MOD_ID, "geo/owl_entity.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(OwlEntity object)
    {
        return new ResourceLocation(PottercraftMod.MOD_ID, "textures/entities/snowy_owl.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(OwlEntity object)
    {
        return new ResourceLocation(PottercraftMod.MOD_ID, "animations/owl_entity.animation.json");
    }

}