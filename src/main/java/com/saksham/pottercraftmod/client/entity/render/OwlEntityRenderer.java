package com.saksham.pottercraftmod.client.entity.render;

import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.client.entity.model.OwlEntityModel;
import com.saksham.pottercraftmod.common.entity.OwlEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OwlEntityRenderer extends GeoEntityRenderer<OwlEntity>{

	public static final ResourceLocation TEXTURE = new ResourceLocation(PottercraftMod.MOD_ID, "textures/entities/snowy_owl.png");

	public OwlEntityRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new OwlEntityModel());
	}

	@Override
	public ResourceLocation getEntityTexture(OwlEntity entity) {
		return TEXTURE;
	}
}
