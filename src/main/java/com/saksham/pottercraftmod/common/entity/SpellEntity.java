package com.saksham.pottercraftmod.common.entity;

import java.util.Timer;
import java.util.TimerTask;

import com.saksham.pottercraftmod.common.init.ModEntityTypes;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpellEntity extends ProjectileItemEntity {
	private static DataParameter<String> SPELL = EntityDataManager.createKey(SpellEntity.class, DataSerializers.STRING);
	
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

	public SpellEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(ModEntityTypes.SPELL_ENTITY.get(), x, y, z, worldIn);
	}

	private double knockbackStrength = 0.1D;
	private double damage = 1;
	protected String currentSpell;
	protected boolean damaged;

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
		if (!world.isRemote) {
			LivingEntity shootingEntity = this.shooter;

			if(result.getType() == RayTraceResult.Type.ENTITY || result.getType() == RayTraceResult.Type.BLOCK){
				BlockPos pos = new BlockPos(result.getHitVec());
				switch (getCurrentSpell()){
					case "confringo":

						world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, true, Explosion.Mode.DESTROY);
						break;
					case "aguamenti":
						world.setBlockState(pos.up(), Blocks.WATER.getDefaultState());
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {

							@Override
							public void run() {
								world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
							}
						}, 10 * 1000);
						break;
				}
			}

			if(result.getType() == RayTraceResult.Type.ENTITY){
				if(((EntityRayTraceResult) result).getEntity() instanceof LivingEntity){
					LivingEntity target = (LivingEntity) ((EntityRayTraceResult) result).getEntity();

					switch (getCurrentSpell()){
						case "avada":
							setDamaged(target.attackEntityFrom(
									(new IndirectEntityDamageSource("spell", this, shootingEntity)).setProjectile(),
									Float.MAX_VALUE));
							break;
						case "expelliarmus":
							ItemStack handItem = target.getHeldItemMainhand();
							shootingEntity.entityDropItem(handItem);
							handItem.shrink(handItem.getCount());
							break;
						case "crucio":
							shootingEntity.setFire(10);
							target.addPotionEffect(new EffectInstance(Effects.WITHER, 600, 2));
							target.addPotionEffect(new EffectInstance(Effects.POISON, 600, 2));
							target.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 600, 2));
							target.addPotionEffect(new EffectInstance(Effects.HUNGER, 600, 2));
							target.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 600, 2));
							target.addPotionEffect(new EffectInstance(Effects.UNLUCK, 600, 2));
							break;
						case "incendio":
							target.setFire(15);
							break;
						case "stupefy":
							setKnockbackStrength(5.0D);
							setDamaged(target.attackEntityFrom(
								(new IndirectEntityDamageSource("spell", this, shootingEntity)).setProjectile(),
									(float) (damage / 2)));
							break;
						case "wingardium_leviosa":
							target.addPotionEffect(new EffectInstance(Effects.LEVITATION, 200));
							break;
						case "accio":
							target.moveToBlockPosAndAngles(shootingEntity.getPosition(), target.rotationYaw,
								this.rotationPitch);
							break;
						case "conjunctivitis":
							target.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 100));
							if(shootingEntity instanceof PlayerEntity){
								target.removeTrackingPlayer((ServerPlayerEntity) shootingEntity);
							}
							break;
						case "petrificus":
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

					if(isDamaged()){
						if (knockbackStrength > 0) {
							Vec3d vec = getMotion().mul(1, 0, 1).normalize().scale(knockbackStrength);

							if (vec.lengthSquared() > 0) {
								target.addVelocity(vec.x, 0.1, vec.z);
							}
						}
					}
				}
			}else if (result.getType() == RayTraceResult.Type.BLOCK){
				BlockPos pos =  ((BlockRayTraceResult) result).getPos();
				System.out.println("this is spell : " + getCurrentSpell());
				if (getCurrentSpell().equals("incendio")) {
					System.out.println("oy");
					summonFire(pos);
				}
			}

			this.remove();
		}

	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putDouble("damage", damage);
		compound.putDouble("knockback", knockbackStrength);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		damage = compound.getDouble("damage");
		knockbackStrength = compound.getDouble("knockback");
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getDamage() {
		return damage;
	}

	public void setKnockbackStrength(double knockbackStrength) {
		this.knockbackStrength = knockbackStrength;
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


	public void setCurrentSpell(String stringIn) {
		this.dataManager.set(SPELL, stringIn);
	}

	public String getCurrentSpell() {
		return this.dataManager.get(SPELL);
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(SPELL, "stupefy");
	}

	protected boolean isDamaged() {
		return damaged;
	}

	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}


}
