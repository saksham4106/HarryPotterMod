package com.saksham.pottercraftmod.client.entity.render;

import com.saksham.pottercraftmod.client.entity.model.UnicornEntityModel;
import com.saksham.pottercraftmod.entity.UnicornEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class UnicornEntityRenderer extends GeoEntityRenderer<UnicornEntity> {

    public UnicornEntityRenderer(EntityRendererManager renderManager){
        super(renderManager, new UnicornEntityModel());
    }
}
