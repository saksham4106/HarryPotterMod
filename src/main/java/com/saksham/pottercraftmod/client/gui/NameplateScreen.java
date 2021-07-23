package com.saksham.pottercraftmod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.saksham.pottercraftmod.core.network.Networking;
import com.saksham.pottercraftmod.core.network.FlooStationPacket;
import com.saksham.pottercraftmod.common.tileentity.NameplateTileEntity;
import com.saksham.pottercraftmod.client.tileentityrenderer.NameplaterRenderer;

import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class NameplateScreen extends Screen {
	private final NameplaterRenderer.NameplateModel signModel = new NameplaterRenderer.NameplateModel();
	private final NameplateTileEntity tileSign;
	private int updateCounter;
	private int editLine;
	private TextInputUtil textInputUtil;

	public NameplateScreen(NameplateTileEntity teSign) {
		super(new TranslationTextComponent("sign.edit"));
		this.tileSign = teSign;
	}

	protected void init() {
		this.minecraft.keyboardListener.enableRepeatEvents(true);
		this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20,new StringTextComponent("Set FlooStation"),
				(p_214266_1_) -> {
					Networking.sendToServer(new FlooStationPacket(1, this.tileSign.getPos(), this.tileSign.getText(0).getString()
							+ this.tileSign.getText(1).getString() + this.tileSign.getText(2).getString() + this.tileSign.getText(3).getString()));

					this.minecraft.displayGuiScreen(null);

				}));
		this.addButton(
				new Button(this.width / 2 - 25, this.height / 4 + 150, 50, 20, new StringTextComponent("CANCEL"), (p_214266_1_) -> {
					this.close();
				}));

		this.textInputUtil = new TextInputUtil(() -> String.valueOf(this.tileSign.getText(editLine)), (text) -> {
			this.tileSign.setText(editLine, new StringTextComponent(text));
		}, TextInputUtil.getClipboardTextSupplier(this.minecraft), TextInputUtil.getClipboardTextSetter(this.minecraft),
				(text) -> this.minecraft.fontRenderer.getStringWidth(text) <= 90);

	}

	public void tick() {
		++this.updateCounter;
		if (!this.tileSign.getType().isValidBlock(this.tileSign.getBlockState().getBlock())) {
			this.close();
		}
	}

	private void close() {
		this.tileSign.markDirty();
		this.minecraft.displayGuiScreen(null);
	}

	public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
		this.textInputUtil.putChar(p_charTyped_1_);
		return true;
	}

	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		if (p_keyPressed_1_ == 265) {
			this.editLine = this.editLine - 1 & 3;
			this.textInputUtil.moveCursorToEnd();
			return true;
		} else if (p_keyPressed_1_ != 264 && p_keyPressed_1_ != 257 && p_keyPressed_1_ != 335) {
			return this.textInputUtil.specialKeyPressed(p_keyPressed_1_) || super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
		} else {
			this.editLine = this.editLine + 1 & 3;
			this.textInputUtil.moveCursorToEnd();
			return true;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		RenderHelper.setupGuiFlatDiffuseLighting();
		this.renderBackground(matrixStack);
		drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 40, 16777215);
		matrixStack.push();
		matrixStack.translate((this.width / 2), 0.0D, 50.0D);
		matrixStack.scale(93.75F, -93.75F, 93.75F);
		matrixStack.translate(0.0D, -1.3125D, 0.0D);
		BlockState blockstate = this.tileSign.getBlockState();

		matrixStack.translate(0.0D, -0.3125D, 0.0D);

		boolean flag1 = this.updateCounter / 6 % 2 == 0;
		matrixStack.push();
		matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
		IRenderTypeBuffer.Impl irendertypebuffer$impl = this.minecraft.getRenderTypeBuffers().getBufferSource();
		RenderMaterial rendermaterial = SignTileEntityRenderer.getMaterial(blockstate.getBlock());
		IVertexBuilder ivertexbuilder = rendermaterial.getBuffer(irendertypebuffer$impl, this.signModel::getRenderType);
		this.signModel.board.render(matrixStack, ivertexbuilder, 15728880, OverlayTexture.NO_OVERLAY);

		matrixStack.pop();
		matrixStack.translate(0.0D, (double)0.33333334F, (double)0.046666667F);
		matrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int i = this.tileSign.getTextColor().getTextColor();
		int j = this.textInputUtil.getSelectionEnd();
		int k = this.textInputUtil.getSelectionStart();
		int l = this.editLine * 10 - 4 * 5;
		Matrix4f matrix4f = matrixStack.getLast().getMatrix();

		for(int i1 = 0; i1 < 4; ++i1) {
			String s = String.valueOf(this.tileSign.getText(i1));
			if (s != null) {
				if (this.font.getBidiFlag()) {
					s = this.font.bidiReorder(s);
				}

				float f3 = (float)(-this.minecraft.fontRenderer.getStringWidth(s) / 2);
				this.minecraft.fontRenderer.drawBidiString(s, f3, (float)(i1 * 10 - 4 * 5), i, false, matrix4f, irendertypebuffer$impl, false, 0, 15728880, false);
				if (i1 == this.editLine && j >= 0 && flag1) {
					int j1 = this.minecraft.fontRenderer.getStringWidth(s.substring(0, Math.max(Math.min(j, s.length()), 0)));
					int k1 = j1 - this.minecraft.fontRenderer.getStringWidth(s) / 2;
					if (j >= s.length()) {
						this.minecraft.fontRenderer.drawBidiString("_", (float)k1, (float)l, i, false, matrix4f, irendertypebuffer$impl, false, 0, 15728880, false);
					}
				}
			}
		}

		irendertypebuffer$impl.finish();

		for(int i3 = 0; i3 < 4; ++i3) {
			String s1 = String.valueOf(this.tileSign.getText(i3));
			if (s1 != null && i3 == this.editLine && j >= 0) {
				int j3 = this.minecraft.fontRenderer.getStringWidth(s1.substring(0, Math.max(Math.min(j, s1.length()), 0)));
				int k3 = j3 - this.minecraft.fontRenderer.getStringWidth(s1) / 2;
				if (flag1 && j < s1.length()) {
					fill(matrixStack, k3, l - 1, k3 + 1, l + 9, -16777216 | i);
				}

				if (k != j) {
					int l3 = Math.min(j, k);
					int l1 = Math.max(j, k);
					int i2 = this.minecraft.fontRenderer.getStringWidth(s1.substring(0, l3)) - this.minecraft.fontRenderer.getStringWidth(s1) / 2;
					int j2 = this.minecraft.fontRenderer.getStringWidth(s1.substring(0, l1)) - this.minecraft.fontRenderer.getStringWidth(s1) / 2;
					int k2 = Math.min(i2, j2);
					int l2 = Math.max(i2, j2);
					Tessellator tessellator = Tessellator.getInstance();
					BufferBuilder bufferbuilder = tessellator.getBuffer();
					RenderSystem.disableTexture();
					RenderSystem.enableColorLogicOp();
					RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
					bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
					bufferbuilder.pos(matrix4f, (float)k2, (float)(l + 9), 0.0F).color(0, 0, 255, 255).endVertex();
					bufferbuilder.pos(matrix4f, (float)l2, (float)(l + 9), 0.0F).color(0, 0, 255, 255).endVertex();
					bufferbuilder.pos(matrix4f, (float)l2, (float)l, 0.0F).color(0, 0, 255, 255).endVertex();
					bufferbuilder.pos(matrix4f, (float)k2, (float)l, 0.0F).color(0, 0, 255, 255).endVertex();
					bufferbuilder.finishDrawing();
					WorldVertexBufferUploader.draw(bufferbuilder);
					RenderSystem.disableColorLogicOp();
					RenderSystem.enableTexture();
				}
			}
		}
		matrixStack.pop();
		RenderHelper.setupGui3DDiffuseLighting();
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
}