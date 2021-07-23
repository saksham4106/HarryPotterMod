package com.saksham.pottercraftmod.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.saksham.pottercraftmod.common.entity.SpellEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SpellEntityModel extends EntityModel<SpellEntity> {
	private final ModelRenderer body;

	public SpellEntityModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-1.0F, -4.0F, -8.0F, 1.0F, 1.0F, 16.0F, 0.0F, false);
		body.setTextureOffset(11, 18).addBox(-1.0F, -3.0F, -5.0F, 1.0F, 1.0F, 9.0F, 0.0F, false);
		body.setTextureOffset(0, 17).addBox(-1.0F, -5.0F, -5.0F, 1.0F, 1.0F, 9.0F, 0.0F, false);
		body.setTextureOffset(0, 4).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		body.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -2.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
	}

//	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
//		modelRenderer.rotateAngleX = x;
//		modelRenderer.rotateAngleY = y;
//		modelRenderer.rotateAngleZ = z;
//	}

	@Override
	public void setRotationAngles(SpellEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
	}
}