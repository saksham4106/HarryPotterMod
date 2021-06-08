//package com.saksham.pottercraftmod.common.particles;
//
//import java.util.Locale;
//
//import com.mojang.brigadier.StringReader;
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import com.saksham.pottercraftmod.common.init.ParticleInit;
//
//import net.minecraft.client.particle.IAnimatedSprite;
//import net.minecraft.client.particle.IParticleFactory;
//import net.minecraft.client.particle.IParticleRenderType;
//import net.minecraft.client.particle.Particle;
//import net.minecraft.client.particle.SpriteTexturedParticle;
//import net.minecraft.network.PacketBuffer;
//import net.minecraft.particles.IParticleData;
//import net.minecraft.particles.ParticleType;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.registry.Registry;
//import net.minecraft.world.World;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//public class WandSpellParticle extends SpriteTexturedParticle {
//	private double posX, posY, posZ;
//	  private final IAnimatedSprite spriteIn;
//
//
//	protected WandSpellParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
//			double ySpeedIn, double zSpeedIn, WandSpellParticleData data, IAnimatedSprite sprite) {
//
//		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
//
//		this.spriteIn = sprite;
//		this.motionX *= (double)0.1F;
//	    this.motionY *= (double)0.1F;
//	    this.motionZ *= (double)0.1F;
//		this.motionX = xSpeedIn;
//		this.motionY = ySpeedIn;
//		this.motionZ = zSpeedIn;
//
//		this.posX = xCoordIn;
//		this.posY = yCoordIn;
//		this.posZ = zCoordIn;
//
//		 float f = (float)(Math.random() * (double)0.3F);
//	     this.particleRed = f;
//	     this.particleGreen = f;
//	     this.particleBlue = f;
//	     this.particleScale *= 0.75F * 2.5f;
//	     this.maxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
//	     this.maxAge = (int)((float)this.maxAge * 2.5f);
//	     this.maxAge = Math.max(this.maxAge, 1);
//	     this.selectSpriteWithAge(sprite);
//
//	}
//
//	@Override
//	public void tick() {
//		 this.prevPosX = this.posX;
//	      this.prevPosY = this.posY;
//	      this.prevPosZ = this.posZ;
//	      if (this.age++ >= this.maxAge) {
//	         this.setExpired();
//	      } else {
//	         this.selectSpriteWithAge(this.spriteIn);
//	         this.motionY += 0.004D;
//	         this.move(this.motionX, this.motionY, this.motionZ);
//	         if (this.posY == this.prevPosY) {
//	            this.motionX *= 1.1D;
//	            this.motionZ *= 1.1D;
//	         }
//
//	         this.motionX *= (double)0.96F;
//	         this.motionY *= (double)0.96F;
//	         this.motionZ *= (double)0.96F;
//	         if (this.onGround) {
//	            this.motionX *= (double)0.7F;
//	            this.motionZ *= (double)0.7F;
//	         }
//
//	      }
//
//	}
//
//
//
//	@Override
//	public IParticleRenderType getRenderType() {
//		return IParticleRenderType.PARTICLE_SHEET_LIT;
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class Factory implements IParticleFactory<WandSpellParticleData> {
//		private final IAnimatedSprite spriteSet;
//
//		public Factory(IAnimatedSprite spriteIn) {
//			this.spriteSet = spriteIn;
//		}
//
//		public Particle makeParticle(WandSpellParticleData typeIn, World worldIn, double x, double y, double z,
//				double xSpeed, double ySpeed, double zSpeed) {
//
//			WandSpellParticle particle = new WandSpellParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn,
//					spriteSet);
//			particle.selectSpriteRandomly(spriteSet);
//			return particle;
//
//		}
//	}
//
//	public static class WandSpellParticleData implements IParticleData {
//		public static final IParticleData.IDeserializer<WandSpellParticleData> DESERIALIZER = new IParticleData.IDeserializer<WandSpellParticleData>() {
//
//			public WandSpellParticleData deserialize(ParticleType<WandSpellParticleData> particalTypeIn, StringReader reader) throws CommandSyntaxException {
//				reader.expect(' ');
//				float red = (float)reader.readDouble();
//				reader.expect(' ');
//				float green = (float)reader.readDouble();
//				reader.expect(' ');
//				float blue = (float)reader.readDouble();
//				reader.expect(' ');
//				float alpha = (float)reader.readDouble();
//				return new WandSpellParticleData(red, green, blue, alpha);
//
//			}
//
//			public WandSpellParticleData read(ParticleType<WandSpellParticleData> particleTypeIn, PacketBuffer buffer) {
//				return new WandSpellParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
//
//			}
//		};
//
//		private final float red;
//		private final float green;
//		private final float blue;
//		private final float alpha;
//
//		public WandSpellParticleData(float redIn, float greenIn, float blueIn, float alphaIn) {
//			this.red = redIn;
//			this.green = greenIn;
//			this.blue = blueIn;
//			this.alpha = MathHelper.clamp(alphaIn, 0.01f, 4.0f);
//		}
//
//		@Override
//		public void write(PacketBuffer buffer) {
//			buffer.writeFloat(this.red);
//			buffer.writeFloat(this.green);
//			buffer.writeFloat(this.blue);
//			buffer.writeFloat(this.alpha);
//		}
//		@SuppressWarnings("deprecation")
//		@Override
//		public String getParameters() {
//			return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()),
//					this.red, this.green, this.blue, this.alpha);
//		}
//
//		@Override
//		public ParticleType<WandSpellParticleData> getType() {
//			return ParticleInit.WAND_SPELL_PARTICLE.get();
//		}
//
//		@OnlyIn(Dist.CLIENT)
//		public float getRed() {
//			return this.red;
//		}
//
//		@OnlyIn(Dist.CLIENT)
//		public float getGreen() {
//			return this.green;
//		}
//
//		@OnlyIn(Dist.CLIENT)
//		public float getBlue() {
//			return this.blue;
//		}
//
//		@OnlyIn(Dist.CLIENT)
//		public float getAlpha() {
//			return this.alpha;
//		}
//	}
//
//}
