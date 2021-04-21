package com.saksham.pottercraftmod.client.gui;

import com.saksham.pottercraftmod.init.BlockInit;
import com.saksham.pottercraftmod.network.FlooStationPacket;
import com.saksham.pottercraftmod.network.Networking;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;

public class FlooTeleportScreen extends Screen {
	private PlayerEntity player;
	private BlockPos pos;
	private TextFieldWidget destinationTextWidget;

	public FlooTeleportScreen(PlayerEntity player, BlockPos pos) {
		super(new TranslationTextComponent("pottercraftmod.screen.flooteleport"));
		this.player = player;
		
		this.pos = pos;
	}

	protected void init() {
		this.minecraft.keyboardListener.enableRepeatEvents(true);
		this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20,
				I18n.format(new TranslationTextComponent("pottercraftmod.button.teleportfloo").getFormattedText()),
				(p_214266_1_) -> {
					Networking.sendToServer(new FlooStationPacket(2, pos, this.destinationTextWidget.getText()));
					this.close();

				}));
		this.addButton(
				new Button(this.width / 2 - 25, this.height / 4 + 150, 50, 20,
						I18n.format(new TranslationTextComponent("pottercraftmod.screen.cancel").getFormattedText()), (p_214266_1_) -> {
					this.close();
				}));
		
		 this.destinationTextWidget = new TextFieldWidget(this.font, this.width / 2 - 150, 50, 300, 20, "hmmm");
		 this.destinationTextWidget.setEnabled(true);
		 this.children.add(this.destinationTextWidget);

	}

	public void removed() {
		this.minecraft.keyboardListener.enableRepeatEvents(false);
	}

	public void tick() {
		if(this.player.world.getBlockState(pos) != BlockInit.FLOO_FIRE.get().getDefaultState()) {
			this.close();
		}
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}

	private void close() {
		this.minecraft.displayGuiScreen((Screen) null);
	}

	public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
		RenderHelper.setupGuiFlatDiffuseLighting();
		this.renderBackground();
		this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 40, 16777215);
		this.destinationTextWidget.render(p_render_1_, p_render_2_, p_render_3_);

		super.render(p_render_1_, p_render_2_, p_render_3_);
	}
	
	
}
