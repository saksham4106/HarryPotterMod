package com.saksham.pottercraftmod.objects.items;

import java.util.function.Predicate;

import com.saksham.pottercraftmod.entity.SpellEntity;
import com.saksham.pottercraftmod.init.ItemInit;
import com.saksham.pottercraftmod.particles.WandSpellParticle;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class WandItem extends ShootableItem {

	protected int spellBuffer;

	public static int currentSpellId = 0;
	public static String[] spellList = { "stupefy", "avada", "crucio", "imperius", "incendio", "aguamenti" };
	public static String[] spellDisplayList = { "Stupefy(Knocks the target)", "Avada Kedavra(The Killing Curse)",
			"Crucio(The Torture Curse)", "Imperio(The Imperius Curse)", "Incendio(Set's fire)",
			"Aguamenti(set's water)" };
	protected String currentSpell = spellList[currentSpellId];

	public WandItem(Properties properties) {
		super(properties);

	}

	public static int getCurrentSpellId() {
		return currentSpellId;
	}

	public static void setCurrentSpellId(int currentSpellId, ServerPlayerEntity playerIn) {
		WandItem.currentSpellId = currentSpellId;
		playerIn.sendMessage(new StringTextComponent("Current Spell Changed to " + spellDisplayList[currentSpellId]));

	}

	public String getCurrentSpell() {
		System.out.println(getCurrentSpellId());
		return spellList[getCurrentSpellId()];
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack wand = playerIn.getHeldItem(handIn);
		ItemStack ammo = new ItemStack(ItemInit.SPELL_BALL.get());
		BlockPos pos = playerIn.getPosition();
		
		worldIn.addParticle(new WandSpellParticle.WandSpellParticleData(0.0F, 1.0F, 0.0F, 1.0F), pos.getX(), pos.getY(), pos.getZ(), 1.0F, 1.0F, 1.0F);

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
