package com.saksham.pottercraftmod.entity;

import com.saksham.pottercraftmod.init.ModEntityTypes;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
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

	public SpellEntity(EntityType<? extends SpellEntity> p_i50160_1_, World p_i50160_2_) {
		super(p_i50160_1_, p_i50160_2_);
	}

	public SpellEntity(World worldIn, LivingEntity shooter) {
		this(worldIn, shooter, 0, 0, 0);
		this.owner = shooter;
		setPosition(shooter.getPosX(), shooter.getPosYEye() - 0.1, shooter.getPosZ());
	}

	public SpellEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(ModEntityTypes.SPELL_ENTITY.get(), accelX, accelY, accelZ, worldIn);
	}

	public SpellEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(ModEntityTypes.SPELL_ENTITY.get(), x, y, z, worldIn);
	}
	
	

	protected int ticksExisted;
	private double knockbackStrength = 0;
	private double damage = 1;
	protected String currentSpell;
	protected boolean damaged;
	protected int currentSpellId;
	private BlockPos waterPos;
	private boolean flag;
	private int timer;

	// public String[] spellList = {"stupefy", "avada", "crucio", "imperius",
	// "incendio", "aguamenti" };

	
	protected void setTimer(int timer) {
		this.timer = timer;
	}


	public void setCurrentSpell(String stringIn) {
		this.currentSpell = stringIn;
	}

	public String getCurrentSpell() {
		return this.currentSpell;
	}

	protected boolean getDamaged() {
		return damaged;
	}

	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}

	@Override
	public void tick() {
		
		if (ticksExisted > 200 || getMotion().lengthSquared() < 0.1) {
			remove();
		}
		ticksExisted++;
		 
		 if (isFlag()) {
			
			 if (timer > 40) {
				 timer = 0;
				 System.out.println(isFlag());
				 world.setBlockState(this.waterPos, Blocks.AIR.getDefaultState());
				 setFlag(false);
				 
			 }
			 timer++;
			 
			 
		 }
		super.tick();
		
	}
	
	
	
	
	

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!world.isRemote) {
			
			if(getCurrentSpell().equals(new String("confringo"))) {
				BlockPos pos = new BlockPos(result.getHitVec());
				world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, true, Explosion.Mode.DESTROY);
				
				
						
			}else if(getCurrentSpell().equals(new String("aguamenti"))) {
				BlockPos pos = new BlockPos(result.getHitVec());
				world.setBlockState(pos.up(), Blocks.WATER.getDefaultState());
				setFlag(true);
				
			}else if(getCurrentSpell().equals(new String("ascendio"))) {
				System.out.println("yes");
				this.move(MoverType.SELF, this.owner.getPositionVec().add(0, 10, 0));
		        this.owner.moveToBlockPosAndAngles(this.owner.getPosition().up(5), this.owner.rotationYaw, this.owner.rotationPitch);
		       
			}
			

			if (result.getType() == RayTraceResult.Type.ENTITY) {
				
				// Entity base_entity = ((EntityRayTraceResult) result).getEntity();

				LivingEntity target = (LivingEntity) ((EntityRayTraceResult) result).getEntity();
				

				LivingEntity shootingEntity = this.owner;
				
				

				if (getCurrentSpell().equals(new String("avada"))) {

					boolean damaged = target.attackEntityFrom(
							(new IndirectEntityDamageSource("arrow", this, shootingEntity)).setProjectile(),
							Float.MAX_VALUE);
					setDamaged(damaged);

				} else if (getCurrentSpell().equals(new String("crucio"))) {
					target.addPotionEffect(new EffectInstance(Effects.POISON, 100, 2));

				} else if (getCurrentSpell().equals(new String("incendio"))) {
					target.setFire(10);

				} else if (getCurrentSpell().equals(new String("stupefy"))) {
					setKnockbackStrength(5.0D);

					boolean damaged = target.attackEntityFrom(
							(new IndirectEntityDamageSource("arrow", this, shootingEntity)).setProjectile(),
							(float) (damage - (damage * 0.5F)));
					setDamaged(damaged);

				} else if(getCurrentSpell().equals(new String("wingardium_leviosa"))) {
					target.addPotionEffect(new EffectInstance(Effects.LEVITATION, 200));
					
				}
				
				else if(getCurrentSpell().equals(new String("accio"))) {
					target.moveToBlockPosAndAngles(shootingEntity.getPosition(), target.rotationYaw,this.rotationPitch );
					
				}else if(getCurrentSpell().equals(new String("conjunctivits"))) {
					target.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 100 ));
					
				}else if(getCurrentSpell().equals(new String("petrificus"))) {
					target.setIdleTime(100);
					
				}
				
				else {
					boolean damaged = target.attackEntityFrom(
							(new IndirectEntityDamageSource("arrow", this, shootingEntity)).setProjectile(),
							(float) damage);
					setDamaged(damaged);
				}
				

				if (getDamaged() && target instanceof LivingEntity) {

					LivingEntity livingTarget = (LivingEntity) target;

					if (knockbackStrength > 0) {

						double actualKnockback = knockbackStrength;

						Vec3d vec = getMotion().mul(1, 0, 1).normalize().scale(actualKnockback);

						if (vec.lengthSquared() > 0)
							livingTarget.addVelocity(vec.x, 0.1, vec.z);
					}
				}

				this.remove();
				
			
			} else if (result.getType() == RayTraceResult.Type.BLOCK) {
				BlockPos pos = ((BlockRayTraceResult) result).getPos();
				if(getCurrentSpell().equals(new String("incendio"))) {
					summonFire(pos);
				}
				
				this.remove();
			}
			

		}
		if (this != null) {
			this.remove();
		}
	}

	// }

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
		// TODO Auto-generated method stub
		return null;
		
	}
	
	public void summonFire(BlockPos pos) {
		world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
		
		if (rand.nextInt(2) == 1){
			world.setBlockState(pos.up().east(), Blocks.FIRE.getDefaultState());
		}
		if (rand.nextInt(2) == 1){
			world.setBlockState(pos.up().west(), Blocks.FIRE.getDefaultState());
		}
		if (rand.nextInt(2) == 1){
			world.setBlockState(pos.up().north(), Blocks.FIRE.getDefaultState());
		}
		if (rand.nextInt(2) == 1){
			world.setBlockState(pos.up().south(), Blocks.FIRE.getDefaultState());
		}
		if (rand.nextInt(2) == 1){
			world.setBlockState(pos.up().north().west(), Blocks.FIRE.getDefaultState());
		}
		if (rand.nextInt(2) == 1){
			world.setBlockState(pos.up().south().east(), Blocks.FIRE.getDefaultState());
		}
		if (rand.nextInt(2) == 1){
			world.setBlockState(pos.up().south().west(), Blocks.FIRE.getDefaultState());
		}
		if (rand.nextInt(2) == 1){
			world.setBlockState(pos.up().north().east(), Blocks.FIRE.getDefaultState());
		}
			
		
	}

	public BlockPos getWaterPos() {
		return waterPos;
	}

	public void setWaterPos(BlockPos waterPos) {
		this.waterPos = waterPos;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
