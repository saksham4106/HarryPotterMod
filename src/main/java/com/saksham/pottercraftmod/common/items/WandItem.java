package com.saksham.pottercraftmod.common.items;

import java.util.Random;
import java.util.function.Predicate;

import com.saksham.pottercraftmod.common.entity.SpellEntity;
import com.saksham.pottercraftmod.common.init.ItemInit;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class WandItem extends ShootableItem {

	private static int currentSpellId = 0;
	public static String[] spellList = { "stupefy", "avada", "crucio", "imperius", "incendio", "confringo",
			"wingardium_leviosa", "accio", "aguamenti", "ascendio", "conjunctivits", "petrificus", "expelliarmus" };

	public static String[] spellDisplayList = { "Stupefy", "Avada Kedavra", "Crucio", "Imperio", "Incendio",
			"Confringo", "Wingardium Leviosa", "Accio", "Aguamenti", "Ascendio", "Conjunctivits",
			"petrificus totalus", "Expelliarmus"};

	private float spellLife;

	public static String currentDisplaySpell = spellDisplayList[currentSpellId];

	public WandItem(Properties properties, float spellLife) {
		super(properties);
		this.spellLife = spellLife;

	}

	public static int getCurrentSpellId() {
		return currentSpellId;

	}

	public static void setCurrentSpellId(int currentSpellId) {
		WandItem.currentSpellId = currentSpellId;
		WandItem.currentDisplaySpell = spellDisplayList[currentSpellId];

	}

	public static String getCurrentSpell() {
		return spellList[getCurrentSpellId()];
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack wand = playerIn.getHeldItem(handIn);
		ItemStack ammo = new ItemStack(ItemInit.SPELL_BALL.get());

		SpellItem spellItem = (SpellItem) (ammo.getItem() instanceof SpellItem ? ammo.getItem()
				: ItemInit.SPELL_BALL.get());
		if(getCurrentSpell().equals("ascendio") && worldIn.getBlockState(playerIn.getPosition().down()) != Blocks.AIR.getDefaultState()) {
			playerIn.setVelocity(0, 2, 0);
		}else {
			if (!worldIn.isRemote) {
				shoot(worldIn, playerIn, wand, ammo, spellItem, this.spellLife);
			}

		}


		playerIn.addStat(Stats.ITEM_USED.get(this));
		playerIn.getCooldownTracker().setCooldown(this, 10);

		return ActionResult.resultSuccess(wand);

	}

	protected void shoot(World worldIn, PlayerEntity playerIn, ItemStack wand, ItemStack spell, SpellItem spellItem,
						 float lifeSeconds) {

		SpellEntity shot = spellItem.createProjectile(worldIn, spell, playerIn, lifeSeconds);
		shot.setDamage((shot.getDamage()));
		shot.setNoGravity(true);

		shot.setCurrentSpell(getCurrentSpell());
		shot.setDirectionAndMovement(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0, (float) 2, 5);
		Random rand = new Random();
		if(rand.nextInt(5) == 2){
			wand.damageItem(1, playerIn, (player) -> player.sendBreakAnimation(Hand.MAIN_HAND));
		}
		worldIn.addEntity(shot);

	}

	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return null;
	}

	@Override
	public int func_230305_d_() {
		return 15;
	}

}
