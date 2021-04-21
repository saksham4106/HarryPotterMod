package com.saksham.pottercraftmod.items;

import com.saksham.pottercraftmod.entity.SpellEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SpellItem extends Item{

	private int damage;

	public SpellItem(Properties properties, int damage) {
		super(properties);
		this.damage = damage;
	}
	
	public SpellEntity createProjectile(World world, ItemStack stack, LivingEntity shooter, float lifeSeconds) {
		SpellEntity entity = new SpellEntity(world, shooter, lifeSeconds);
		entity.setDamage(damage);
		return entity;
	}
	


	

	
	

}
