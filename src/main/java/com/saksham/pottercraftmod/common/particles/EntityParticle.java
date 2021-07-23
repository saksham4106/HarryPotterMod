package com.saksham.pottercraftmod.common.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;

public class EntityParticle extends SpriteTexturedParticle {
    private double coordX, coordY, coordZ;

    public EntityParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY,
                             double motionZ, IAnimatedSprite sprite) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.selectSpriteRandomly(sprite);
        this.coordX = x;
        this.coordY = y;
        this.coordZ = z;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;

    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }


}
