package com.teamabode.guarding.core.mixin;

import com.teamabode.guarding.core.init.GuardingItems;
import com.teamabode.guarding.core.init.GuardingSounds;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class NetheriteShieldMixin extends LivingEntity {

    @Shadow public abstract ItemCooldowns getCooldowns();

    protected NetheriteShieldMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract void awardStat(Stat<?> stat);

    @Inject(method = "hurtCurrentlyUsedShield", at = @At("HEAD"), cancellable = true)
    private void guarding$hurtCurrentlyUsedShield(float damageAmount, CallbackInfo ci) {
        if (useItem.is(GuardingItems.NETHERITE_SHIELD)) {
            if (!level().isClientSide) this.awardStat(Stats.ITEM_USED.get(useItem.getItem()));
            if (damageAmount >= 3.0f) {
                int damage = 1 + Mth.floor(damageAmount);
                InteractionHand hand = this.getUsedItemHand();

                useItem.hurtAndBreak(damage, this, getSlotForHand(hand));
                if (useItem.isEmpty()) {
                    this.setItemSlot(hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    this.useItem = ItemStack.EMPTY;
                    this.playSound(GuardingSounds.ITEM_NETHERITE_SHIELD_BREAK, 0.8f, 0.8f + this.level().random.nextFloat() * 0.4f);
                }
            }
            ci.cancel();
        }
    }

    @Inject(method = "disableShield", at = @At("HEAD"))
    private void guarding$disableShield(CallbackInfo ci) {
        this.getCooldowns().addCooldown(GuardingItems.NETHERITE_SHIELD, 100);
        this.stopUsingItem();
        this.level().broadcastEntityEvent(this, (byte)30);
    }
}
