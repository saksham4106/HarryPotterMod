package com.saksham.pottercraftmod.common.items;

import com.saksham.pottercraftmod.common.entity.SpellEntity;
import com.saksham.pottercraftmod.core.util.SpellsList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class SimpleWandItem extends ShootableItem {
    private SpellsList currentSpell = SpellsList.values()[0];
    private final float spellLife;
    private final float damage;

    public SimpleWandItem(Properties builder) {
        super(builder);
        this.spellLife = 1.25f;
        this.damage = 0.2f;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack wand = playerIn.getHeldItem(Hand.MAIN_HAND);
        if(!worldIn.isRemote) {
            this.shoot(worldIn, playerIn);
        }else{
            this.performNonShootingSpells(playerIn, worldIn);
        }

        if(random.nextInt(5) == 2) {
            wand.damageItem(1, playerIn,
                    playerEntity -> playerEntity.sendBreakAnimation(Hand.MAIN_HAND));
        }

        playerIn.getCooldownTracker().setCooldown(this, 10);
        playerIn.addStat(Stats.ITEM_USED.get(this));
        return ActionResult.resultSuccess(wand);
    }

    public void nextSpell(){
        currentSpell = currentSpell.next();
    }

    public SpellsList getCurrentSpell(){
        return this.currentSpell;
    }

    private void performNonShootingSpells(PlayerEntity player, World world){
        if(currentSpell == SpellsList.ASCENDIO &&
                world.getBlockState(player.getPosition().down()) != Blocks.AIR.getDefaultState()){
            player.setVelocity(0, 2 ,0 );

        }
    }

    private void shoot(World world, PlayerEntity player){
        SpellEntity spellEntity = new SpellEntity(world, player, this.spellLife);
        spellEntity.setDamage(this.damage);
        spellEntity.setNoGravity(true);
        spellEntity.setCurrentSpell(currentSpell);
        spellEntity.setDirectionAndMovement(player, player.rotationPitch, player.rotationYaw, 0, (float) 2, 5);
        world.addEntity(spellEntity);
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
