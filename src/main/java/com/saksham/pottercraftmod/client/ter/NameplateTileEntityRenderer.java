package com.saksham.pottercraftmod.client.ter;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.saksham.pottercraftmod.PottercraftMod;
import com.saksham.pottercraftmod.init.ModTileEntities;
import com.saksham.pottercraftmod.objects.blocks.NameplateBlock;
import com.saksham.pottercraftmod.objects.tileentity.NameplateTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;


@OnlyIn(Dist.CLIENT)
public class NameplateTileEntityRenderer extends TileEntityRenderer<NameplateTileEntity>{
	
	 private final NameplateTileEntityRenderer.NameplateModel model = new NameplateTileEntityRenderer.NameplateModel();
	  public static final ResourceLocation NAMEPLATE_TEXTURE = new ResourceLocation(PottercraftMod.MOD_ID, "block/nameplate");

	public NameplateTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);

	}

	   public void render(NameplateTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		      BlockState blockstate = tileEntityIn.getBlockState();
		      matrixStackIn.push();
		      
		      if (blockstate.getBlock() instanceof NameplateBlock) {
		         matrixStackIn.translate(0.5D, 0.5D, 0.5D);
		         this.model.signStick.showModel = true;
		      }

		      matrixStackIn.push();
		      matrixStackIn.scale(0.6666667F, -0.6666667F, -0.6666667F);
		      Material material = new Material(NAMEPLATE_TEXTURE, new ResourceLocation(PottercraftMod.MOD_ID + "block/nameplate"));
		      IVertexBuilder builder = material.getBuffer(bufferIn, this.model::getRenderType);
		    
		      this.model.signBoard.render(matrixStackIn, builder, combinedLightIn, combinedOverlayIn);
		      this.model.signStick.render(matrixStackIn, builder, combinedLightIn, combinedOverlayIn);
		      matrixStackIn.pop();
		      FontRenderer fontrenderer = this.renderDispatcher.getFontRenderer();
		      
		      matrixStackIn.translate(0.0D, (double)0.33333334F, (double)0.046666667F);
		      matrixStackIn.scale(0.010416667F, -0.010416667F, 0.010416667F);
		      int i = tileEntityIn.textColor.getTextColor();
		      
		      int j = (int)((double)NativeImage.getRed(i) * 0.4D);
		      int k = (int)((double)NativeImage.getGreen(i) * 0.4D);
		      int l = (int)((double)NativeImage.getBlue(i) * 0.4D);
		      int i1 = NativeImage.getCombined(0, l, k, j);

		      for(int j1 = 0; j1 < 4; ++j1) {
		         String s = tileEntityIn.getRenderText(j1, (p_212491_1_) -> {
		            List<ITextComponent> list = RenderComponentsUtil.splitText(p_212491_1_, 90, fontrenderer, false, true);
		            return list.isEmpty() ? "" : list.get(0).getFormattedText();
		         });
		         if (s != null) {
		            float f3 = (float)(-fontrenderer.getStringWidth(s) / 2);
		            fontrenderer.renderString(s, f3, (float)(j1 * 10 - tileEntityIn.nameplateText.length * 5), i1, false, matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, combinedLightIn);
		         }
		      }

		      matrixStackIn.pop();
		   }
	
	public static void register() {
		ClientRegistry.bindTileEntityRenderer(ModTileEntities.NAMEPLATE.get(), NameplateTileEntityRenderer::new);
	}
	
	
	
	
	@OnlyIn(Dist.CLIENT)
	   public static final class NameplateModel extends Model {
	      public final ModelRenderer signBoard = new ModelRenderer(64, 32, 0, 0);
	      public final ModelRenderer signStick;

	      public NameplateModel() {
	         super(RenderType::getEntityCutoutNoCull);
	         this.signBoard.addBox(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F, 0.0F);
	         this.signStick = new ModelRenderer(64, 32, 0, 14);
	         this.signStick.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F);
	      }

	      public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
	         this.signBoard.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	         this.signStick.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	      }
	   }
	

}
