package com.saksham.pottercraftmod.common.tileentity.tileentityrenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.common.tileentity.NameplateTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

public class NameplaterRenderer extends TileEntityRenderer<NameplateTileEntity> {

	private final NameplateModel model = new NameplateModel();

	public NameplaterRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(NameplateTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		System.out.println("tick");
		BlockState blockstate = tileEntityIn.getBlockState();
		matrixStackIn.push();
		matrixStackIn.translate(0.5D, 0.5D, 0.5D);
		float f4 = -blockstate.get(WallSignBlock.FACING).getHorizontalAngle();
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f4));
		matrixStackIn.translate(0.0D, -0.3125D, -0.4375D);

		matrixStackIn.push();
		matrixStackIn.scale(0.7666667F, -0.7666667F, -0.6666667F);
		Material material = NameplaterRenderer.NameplateModel.MATERIAL;
		IVertexBuilder ivertexbuilder = material.getBuffer(bufferIn, this.model::getRenderType);
		this.model.board.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
		FontRenderer fontrenderer = this.renderDispatcher.getFontRenderer();

		matrixStackIn.translate(0.0D, 0.33333334F, 0.046666667F);
		matrixStackIn.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int textColor = 1037857135;

		int j = (int) ((double) NativeImage.getRed(textColor) * 0.4D);
		int k = (int) ((double) NativeImage.getGreen(textColor) * 0.4D);
		int l = (int) ((double) NativeImage.getBlue(textColor) * 0.4D);
		int i1 = NativeImage.getCombined(0, l, k, j);
		
		for (int j1 = 0; j1 < 4; ++j1) {
			String s = tileEntityIn.getText(j1).getString();
			if (s != null) {
				float f3 = (float) (-fontrenderer.getStringWidth(s) / 2);

				fontrenderer.renderString(s, f3, (float) (j1 * 10 - tileEntityIn.signText.length * 5), i1, false,
						matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, combinedLightIn);
			}
		}

		matrixStackIn.pop();
	}

	public static final class NameplateModel extends Model {
		public final ModelRenderer board = new ModelRenderer(64, 32, 0, 0);
		public static final Material MATERIAL = new Material(Atlases.SIGN_ATLAS,
				new ResourceLocation(PottercraftMod.MOD_ID, "entities/nameplate"));

		public NameplateModel() {
			super(RenderType::getEntityCutoutNoCull);
			this.board.addBox(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F, 0.0F);
		}

		public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
				float red, float green, float blue, float alpha) {
			this.board.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		}
	}

}
