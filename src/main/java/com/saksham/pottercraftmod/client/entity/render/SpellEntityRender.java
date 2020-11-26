package com.saksham.pottercraftmod.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.client.entity.model.SpellEntityModel;
import com.saksham.pottercraftmod.entity.SpellEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class SpellEntityRender extends EntityRenderer<SpellEntity> {
	private final SpellEntityModel modelIn = new SpellEntityModel();
	

	public SpellEntityRender(EntityRendererManager renderManager) {
		super(renderManager);
		// TODO Auto-generated constructor stub
	}


	protected static final ResourceLocation TEXTURE = new ResourceLocation(PottercraftMod.MOD_ID,
			"textures/entity/example_entity.png");
	


	@Override
	public void render(SpellEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		
		matrixStackIn.push();
	    matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
	    IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.modelIn.getRenderType(TEXTURE));
	    this.modelIn.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.pop();
		
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(SpellEntity entity) {
		return TEXTURE;
	}
}