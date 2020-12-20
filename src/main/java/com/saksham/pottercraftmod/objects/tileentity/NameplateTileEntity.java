package com.saksham.pottercraftmod.objects.tileentity;

import java.util.function.Function;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.saksham.pottercraftmod.init.ModTileEntities;

import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NameplateTileEntity extends SignTileEntity{

	
   public final ITextComponent[] nameplateText = new ITextComponent[]{new StringTextComponent(""), new StringTextComponent(""), new StringTextComponent(""), new StringTextComponent("")};
   private boolean isEditable = true;
   private PlayerEntity player;
   private final String[] renderText = new String[4];
   public DyeColor textColor = DyeColor.BLACK;
   
	public NameplateTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NameplateTileEntity() {
		this(ModTileEntities.NAMEPLATE.get());

	}
	
	 public CompoundNBT write(CompoundNBT compound) {
	      super.write(compound);

	      for(int i = 0; i < 4; ++i) {
	         String s = ITextComponent.Serializer.toJson(this.nameplateText[i]);
	         compound.putString("Text" + (i + 1), s);
	      }

	      return compound;
	   }
	 

	   @OnlyIn(Dist.CLIENT)
	   public ITextComponent getText(int line) {
	      return this.nameplateText[line];
	   }
	   
	 
	   

	   @Nullable
	   @OnlyIn(Dist.CLIENT)
	   public String getRenderText(int line, Function<ITextComponent, String> functionIn) {
	      if (this.renderText[line] == null && this.nameplateText[line] != null) {
	         this.renderText[line] = functionIn.apply(this.nameplateText[line]);
	      }

	      return this.renderText[line];
	   }
	   
	   @Nullable
	   public SUpdateTileEntityPacket getUpdatePacket() {
	      return new SUpdateTileEntityPacket(this.pos, 9, this.getUpdateTag());
	   }

	   public CompoundNBT getUpdateTag() {
		      return this.write(new CompoundNBT());
		   }
	   
	   
	   public boolean onlyOpsCanSetNbt() {
		      return true;
		   }
	
	   public boolean getIsEditable() {
	      return this.isEditable;
	   }
	   
	   public void setPlayer(PlayerEntity playerIn) {
		      this.player = playerIn;
		   }

	   public PlayerEntity getPlayer() {
	      return this.player;
	   }
	 
	 public void read(CompoundNBT compound) {
	      this.isEditable = false;
	      super.read(compound);

	      for(int i = 0; i < 4; ++i) {
	         String s = compound.getString("Text" + (i + 1));
	         ITextComponent itextcomponent = ITextComponent.Serializer.fromJson(s.isEmpty() ? "\"\"" : s);
	         if (this.world instanceof ServerWorld) {
	            try {
	               this.nameplateText[i] = TextComponentUtils.updateForEntity(this.getCommandSource((ServerPlayerEntity)null), itextcomponent, (Entity)null, 0);
	            } catch (CommandSyntaxException var6) {
	               this.nameplateText[i] = itextcomponent;
	            }
	         } else {
	            this.nameplateText[i] = itextcomponent;
	         }

	         this.renderText[i] = null;
	      }

	   }
	 
	 public boolean executeCommand(PlayerEntity playerIn) {
	      for(ITextComponent itextcomponent : this.nameplateText) {
	         Style style = itextcomponent == null ? null : itextcomponent.getStyle();
	         if (style != null && style.getClickEvent() != null) {
	            ClickEvent clickevent = style.getClickEvent();
	            if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
	               playerIn.getServer().getCommandManager().handleCommand(this.getCommandSource((ServerPlayerEntity)playerIn), clickevent.getValue());
	            }
	         }
	      }

	      return true;
	   }
	 
	 public CommandSource getCommandSource(@Nullable ServerPlayerEntity playerIn) {
	      String s = playerIn == null ? "Nameplate" : playerIn.getName().getString();
	      ITextComponent itextcomponent = (ITextComponent)(playerIn == null ? new StringTextComponent("Nameplate") : playerIn.getDisplayName());
	      return new CommandSource(ICommandSource.DUMMY, new Vec3d((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D), Vec2f.ZERO, (ServerWorld)this.world, 2, s, itextcomponent, this.world.getServer(), playerIn);
	   }
	 
	 

}
