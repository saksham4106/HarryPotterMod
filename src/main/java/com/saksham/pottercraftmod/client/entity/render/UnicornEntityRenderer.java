package com.saksham.pottercraftmod.client.entity.render;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.client.entity.model.UnicornEntityModel;
import com.saksham.pottercraftmod.common.entity.OwlEntity;
import com.saksham.pottercraftmod.common.entity.UnicornEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class UnicornEntityRenderer extends GeoEntityRenderer<UnicornEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(PottercraftMod.MOD_ID, "textures/entities/unicorn.png");

    public UnicornEntityRenderer(EntityRendererManager renderManager){
        super(renderManager, new UnicornEntityModel());
    }

    @Override
    public ResourceLocation getEntityTexture(UnicornEntity entity) {
        return TEXTURE;
    }
}
