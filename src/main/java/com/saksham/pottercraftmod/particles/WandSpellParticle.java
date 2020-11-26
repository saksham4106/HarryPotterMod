package com.saksham.pottercraftmod.particles;

import java.util.Locale;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.saksham.pottercraftmod.init.ParticleInit;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WandSpellParticle extends SpriteTexturedParticle {

	private double posX, posY, posZ;

	protected WandSpellParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn, WandSpellParticleData data, IAnimatedSprite sprite) {

		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);

		this.motionX = xSpeedIn;
		this.motionY = ySpeedIn;
		this.motionZ = zSpeedIn;

		this.posX = xCoordIn;
		this.posY = yCoordIn;
		this.posZ = zCoordIn;

		this.particleScale = 0.1F * (this.rand.nextFloat() * 0.2F + 20.0F);
		float f = (float) Math.random() * 0.4F + 0.6F;
		this.particleRed = ((float) (Math.random() * (double) 0.2F) + 0.8F) * data.getRed() * f;
		this.particleGreen = ((float) (Math.random() * (double) 0.2F) + 0.8F) * data.getGreen() * f;
		this.particleBlue = ((float) (Math.random() * (double) 0.2F) + 0.8F) * data.getBlue() * f;
		this.maxAge = (int) (Math.random() * 10.0d) + 40;
		
		this.particleGravity = 0.15f;

	}

	@Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.age++ >= this.maxAge) {
			this.setExpired();
		} else {
			 this.motionY += 0.004D;
	         this.move(this.motionX, this.motionY, this.motionZ);
	         this.motionX *= (double)0.9F;
	         this.motionY *= (double)0.9F;
	         this.motionZ *= (double)0.9F;
	         if (this.onGround) {
	            this.motionX *= (double)0.7F;
	            this.motionZ *= (double)0.7F;
	         }
	         this.motionY -= 0.004D + 0.04D * (double)this.particleGravity;
		}

	}
	
	

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_LIT;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<WandSpellParticleData> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite spriteIn) {
			this.spriteSet = spriteIn;
		}

		public Particle makeParticle(WandSpellParticleData typeIn, World worldIn, double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) {

			WandSpellParticle particle = new WandSpellParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn,
					spriteSet);
			particle.selectSpriteRandomly(spriteSet);
			return particle;

		}
	}

	public static class WandSpellParticleData implements IParticleData {
		public static final IParticleData.IDeserializer<WandSpellParticleData> DESERIALIZER = new IParticleData.IDeserializer<WandSpellParticleData>() {
			
			public WandSpellParticleData deserialize(ParticleType<WandSpellParticleData> particalTypeIn, StringReader reader) throws CommandSyntaxException {
				reader.expect(' ');
				float red = (float)reader.readDouble();
				reader.expect(' ');
				float green = (float)reader.readDouble();
				reader.expect(' ');
				float blue = (float)reader.readDouble();
				reader.expect(' ');
				float alpha = (float)reader.readDouble();
				return new WandSpellParticleData(red, green, blue, alpha);
				
			}
			
			public WandSpellParticleData read(ParticleType<WandSpellParticleData> particleTypeIn, PacketBuffer buffer) {
				return new WandSpellParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
				
			}
		};
		
		private final float red;
		private final float green;
		private final float blue;
		private final float alpha;

		public WandSpellParticleData(float redIn, float greenIn, float blueIn, float alphaIn) {
			this.red = redIn;
			this.green = greenIn;
			this.blue = blueIn;
			this.alpha = MathHelper.clamp(alphaIn, 0.01f, 4.0f);
		}

		@Override
		public void write(PacketBuffer buffer) {
			buffer.writeFloat(this.red);
			buffer.writeFloat(this.green);
			buffer.writeFloat(this.blue);
			buffer.writeFloat(this.alpha);
		}
		@SuppressWarnings("deprecation")
		@Override
		public String getParameters() {
			return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()),
					this.red, this.green, this.blue, this.alpha);
		}

		@Override
		public ParticleType<WandSpellParticleData> getType() {
			return ParticleInit.WAND_SPELL_PARTICLE.get();
		}

		@OnlyIn(Dist.CLIENT)
		public float getRed() {
			return this.red;
		}

		@OnlyIn(Dist.CLIENT)
		public float getGreen() {
			return this.green;
		}

		@OnlyIn(Dist.CLIENT)
		public float getBlue() {
			return this.blue;
		}

		@OnlyIn(Dist.CLIENT)
		public float getAlpha() {
			return this.alpha;
		}
	}
	

}
