package com.saksham.pottercraftmod.common.entity;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LandOnOwnersShoulderGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class OwlEntity extends ShoulderRidingEntity implements IFlyingAnimal, IAnimatable{

	//private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
	private final AnimationFactory factory = new AnimationFactory(this);

	
	public OwlEntity(EntityType<? extends ShoulderRidingEntity> type, World worldIn) {
		super(type, worldIn);
	    this.moveController = new FlyingMovementController(this, 20, true);
	    this.lookController = new LookController(this);
	    this.navigator = createNavigator(worldIn);
	    this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
	    this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0F);
	}
	
	@Override
	public void tick() {
		Random num = new Random();
		Vector3d vec3d = new Vector3d(this.getPosition().getX(), this.getPosition().getZ(), this.getPosition().getY());

		int k= (int) (this.getPosX() + num.nextInt(10));
		int l= (int) (this.getPosY() + num.nextInt(10));
		int i= (int) (this.getPosZ() + num.nextInt(10));

		Vector3d vec3d1 = RandomPositionGenerator.func_226344_b_(this, k, l, i, vec3d, (double)((float)Math.PI / 10F));
		 if (vec3d1 != null) {
			 this.navigator.tryMoveToXYZ(vec3d1.getX(), vec3d1.getY(), vec3d1.getZ(), 1.0D);
		 }
	}



	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2F)
				.createMutableAttribute(Attributes.FLYING_SPEED, 0.8f).createMutableAttribute(Attributes.MAX_HEALTH, 6.0F);
	}

	@Override
	protected void registerGoals() {

	    this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
	    this.goalSelector.addGoal(0, new SwimGoal(this));
	    this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
	    this.goalSelector.addGoal(2, new SitGoal(this));
	    this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
	    this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
	    this.goalSelector.addGoal(3, new LandOnOwnersShoulderGoal(this));
	    
	}

	@Override
	protected PathNavigator createNavigator(World worldIn) {
	    FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn);
	    flyingpathnavigator.setCanOpenDoors(false);
	    flyingpathnavigator.setCanSwim(true);
	    flyingpathnavigator.setCanEnterDoors(true);
	    return flyingpathnavigator;
	}
	
	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
	    return sizeIn.height * 0.6F;
	}

	
	public boolean onLivingFall(float distance, float damageMultiplier) {
		  return false;
	   }

	protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	public boolean canMateWith(AnimalEntity otherAnimal) {
	  return false;
	}

   public boolean isFlying() {
	      return !this.onGround;
	   }
   
   

   protected boolean makeFlySound() {
      return true;
   }


   protected float getSoundPitch() {
      return getPitch(this.rand);
   }

   private static float getPitch(Random random) {
      return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
   }

   public SoundCategory getSoundCategory() {
      return SoundCategory.NEUTRAL;
   }


   public boolean canBePushed() {
      return true;
   }

   protected void collideWithEntity(Entity entityIn) {
      if (!(entityIn instanceof PlayerEntity)) {
         super.collideWithEntity(entityIn);
      }
   }


   private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
   {
	   if(this.isFlying()) {
		   event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.owl.flap", true));
	       return PlayState.CONTINUE;
	   }
	   
	   return PlayState.CONTINUE;
      
   }

   @Override
   public void registerControllers(AnimationData data)
   {
       data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
   }

   @Override
   public AnimationFactory getFactory()
   {
       return this.factory;
   }


	@Nullable
	@Override
	public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
		return null;
	}
}
