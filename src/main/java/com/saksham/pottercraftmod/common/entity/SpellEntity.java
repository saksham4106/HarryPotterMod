package com.saksham.pottercraftmod.common.entity;

import java.util.Timer;
import java.util.TimerTask;

import com.saksham.pottercraftmod.common.registry.ModEntityTypes;

import com.saksham.pottercraftmod.core.util.SpellsList;
import static com.saksham.pottercraftmod.core.util.SpellsList.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpellEntity extends ProjectileItemEntity {

	private static final DataParameter<Integer> SPELL = EntityDataManager.createKey(SpellEntity.class, DataSerializers.VARINT);
	
	public LivingEntity shooter;
	public float lifeSeconds;
	private int ticksExisted;

	public SpellEntity(EntityType<? extends SpellEntity> entity, World worldIn) {
		super(entity, worldIn);
	}

	public SpellEntity(World worldIn, LivingEntity shooter, float lifeSeconds) {
		this(worldIn, shooter, 0, 0, 0);
		this.shooter = shooter;
		this.lifeSeconds = lifeSeconds;
		setPosition(shooter.getPosX(), shooter.getPosYEye() - 0.1, shooter.getPosZ());
	}

	public SpellEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(ModEntityTypes.SPELL_ENTITY.get(), accelX, accelY, accelZ, worldIn);
	}

	private double damage = 1;
	protected boolean damaged;
	private float knockBack = 1.0f;

	@Override
	public void tick() {
		super.tick();
		this.ticksExisted++;
		if(this.ticksExisted > 20 * lifeSeconds){
          //  this.remove();
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if(world.isRemote){
			return;
		}
		LivingEntity shootingEntity = this.shooter;
		switch(result.getType()) {
			case ENTITY:
				if (((EntityRayTraceResult) result).getEntity() instanceof LivingEntity) {
					LivingEntity target = (LivingEntity) ((EntityRayTraceResult) result).getEntity();
					switch (getCurrentSpell()) {
						case AVADA_KEDAVRA:
							target.attackEntityFrom(new IndirectEntityDamageSource(
									"spell", this, shootingEntity).setProjectile(), Float.MAX_VALUE + 1);
							break;
						case EXPELLIARMUS:
							ItemStack heldItem = target.getHeldItem(Hand.MAIN_HAND);
							target.entityDropItem(heldItem.getItem());
							heldItem.shrink(1);
							break;
						case CRUCIO:
							shootingEntity.setFire(10);
							target.addPotionEffect(new EffectInstance(Effects.WITHER, 600, 2));
							target.addPotionEffect(new EffectInstance(Effects.POISON, 600, 2));
							target.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 600, 2));
							target.addPotionEffect(new EffectInstance(Effects.HUNGER, 600, 2));
							target.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 600, 2));
							target.addPotionEffect(new EffectInstance(Effects.UNLUCK, 600, 2));
							break;
						case INCENDIO:
							target.setFire(15);
							break;
						case STUPEFY:
							this.knockBack = 5.0f;
							setDamaged(target.attackEntityFrom(
									(new IndirectEntityDamageSource("spell", this, shootingEntity)).setProjectile(),
									(float) (damage / 2)));
							break;
						case WINGARDIUM_LEVIOSA:
							target.addPotionEffect(new EffectInstance(Effects.LEVITATION, 200));
							break;
						case ACCIO:
							target.moveToBlockPosAndAngles(shootingEntity.getPosition(), target.rotationYaw,
									this.rotationPitch);
							break;
						case CONJUNCTIVITIS:
							target.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 100));
							break;
						case PETRIFICUS:
							if (target instanceof MobEntity) {
								MobEntity mob = (MobEntity) target;
								mob.setNoAI(true);
								Timer timer = new Timer();
								timer.schedule(new TimerTask() {

									@Override
									public void run() {
										mob.setNoAI(false);

									}
								}, 30 * 1000);
							}
							break;
						default:
							boolean damaged = target.attackEntityFrom(
									(new IndirectEntityDamageSource("spell", this, shootingEntity)).setProjectile(),
									(float) damage);
							setDamaged(damaged);
							break;
					}
					if (damaged) {
						if (knockBack > 0) {
							Vector3d vec = getMotion().mul(1, 0, 1).normalize().scale(knockBack);

							if (vec.lengthSquared() > 0) {
								target.addVelocity(vec.x, 0.1, vec.z);
							}
						}
					}

				}
				break;
			case BLOCK:
				if (getCurrentSpell() == INCENDIO) {
					BlockPos pos = ((BlockRayTraceResult) result).getPos();
					summonFire(pos);
				}
				break;
		}
		if (result.getType() != RayTraceResult.Type.MISS) {
			BlockPos pos = new BlockPos(result.getHitVec());
			switch (getCurrentSpell()) {
				case CONFRINGO:
					world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, true, Explosion.Mode.DESTROY);
					break;
				case AGUAMENTI:
					world.setBlockState(pos.up(), Blocks.WATER.getDefaultState());
					new Timer().schedule(new TimerTask() {

						@Override
						public void run() {
							world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
						}
					}, 10 * 1000);
			}
		}
		this.remove();
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putDouble("damage", damage);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		damage = compound.getDouble("damage");
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected Item getDefaultItem() {
		return null;
	}

	public void summonFire(BlockPos pos) {
		world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
		for(int x = 0; x < 4; x++){
			for(int z = 0; z < 4; z++){
				if(rand.nextBoolean()){
					world.setBlockState(pos.add(x, 1, z), Blocks.FIRE.getDefaultState());
				}
			}
		}
	}

	public void setDamage(float damage){
		this.damage = damage;
	}
	public void setCurrentSpell(SpellsList spell) {
		this.dataManager.set(SPELL, spell.ordinal());
	}

	public SpellsList getCurrentSpell() {
		return SpellsList.values()[this.dataManager.get(SPELL)];
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(SPELL, SpellsList.values()[0].ordinal());
	}

	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}
}
