package com.saksham.pottercraftmod.objects.items;

import java.util.function.Predicate;

import com.saksham.pottercraftmod.entity.SpellEntity;
import com.saksham.pottercraftmod.init.ItemInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class WandItem extends ShootableItem {

	protected int spellBuffer;

	public static int currentSpellId = 0;
	public static String[] spellList = { "stupefy", "avada", "crucio", "imperius", "incendio", "confringo",
			"wingardium_leviosa", "accio", "aguamenti", "ascendio", "conjunctivits", "petrificus" };

	public static String[] spellDisplayList = { "Stupefy", "Avada Kedavra", "Crucio", "Imperio", "Incendio",
			"Confringo", "Wingardium Leviosa", "Accio", "Aguamenti", "Ascendio", "Conjunctivits",
			"petrificus totalus" };

	public static String currentSpell = spellList[currentSpellId];
	public static String currentDisplaySpell = spellDisplayList[currentSpellId];

	public WandItem(Properties properties) {
		super(properties);

	}

	public static int getCurrentSpellId() {

		return currentSpellId;

	}

	public static void setCurrentSpellId(int currentSpellId) {
		WandItem.currentSpellId = currentSpellId;
		
		WandItem.currentSpell = spellList[currentSpellId];
		WandItem.currentDisplaySpell = spellDisplayList[currentSpellId];

	}

	public static String getCurrentSpell() {
		return spellList[getCurrentSpellId()];
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(isSelected) {
			if(getCurrentSpellId()!=stack.getOrCreateTag().getInt("SpellId") ) {
				setCurrentSpellId(stack.getOrCreateTag().getInt("SpellId"));
			}else {
				stack.getOrCreateTag().putInt("SpellId", getCurrentSpellId());
			}
	
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack wand = playerIn.getHeldItem(handIn);
		ItemStack ammo = new ItemStack(ItemInit.SPELL_BALL.get());

		SpellItem spellItem = (SpellItem) (ammo.getItem() instanceof SpellItem ? ammo.getItem()
				: ItemInit.SPELL_BALL.get());
		
		if (!worldIn.isRemote) {
			shoot(worldIn, playerIn, wand, ammo, spellItem);
		}

		playerIn.addStat(Stats.ITEM_USED.get(this));
		playerIn.getCooldownTracker().setCooldown(this, 10);

		return ActionResult.resultConsume(wand);

	}

	protected void shoot(World worldIn, PlayerEntity playerIn, ItemStack wand, ItemStack spell, SpellItem spellItem) {

		SpellEntity shot = spellItem.createProjectile(worldIn, spell, playerIn);
		shot.setDamage((shot.getDamage()));
		shot.setNoGravity(true);
		shot.setCurrentSpell(getCurrentSpell());
		shot.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0, (float) 3, 0);
		worldIn.addEntity(shot);

	}

	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return null;
	}

}
