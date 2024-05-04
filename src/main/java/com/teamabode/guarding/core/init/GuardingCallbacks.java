package com.teamabode.guarding.core.init;

import com.teamabode.guarding.Guarding;
import com.teamabode.guarding.GuardingConfig;
import com.teamabode.guarding.core.access.ProjectileAccessor;
import com.teamabode.sketch.core.api.event.ShieldEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GuardingCallbacks {

    public static void init() {
        ShieldEvents.BLOCKED.register(GuardingCallbacks::onBlocked);
    }

    // Logic for blocking
    private static void onBlocked(Player user, DamageSource source, float amount) {
        Entity sourceEntity = source.getDirectEntity();
        boolean isParry = (user.getUseItem().getUseDuration() - user.getUseItemRemainingTicks()) <= 3;
        if (sourceEntity instanceof LivingEntity attacker) blockLivingEntity(user, attacker, isParry);
        if (sourceEntity instanceof Projectile projectile) blockProjectile(user, source.getEntity(), projectile, isParry);

        if (isParry) {
            parryEffects(user, sourceEntity);
        }
    }

    // Logic for blocking a living entity
    private static void blockLivingEntity(Player user, LivingEntity attacker, boolean isParry) {
        if (isParry) {
            float exhaustion = GuardingConfig.INSTANCE.exhaustionCost.get();
            float strength = getKnockbackStrength(user.getUseItem());

            user.causeFoodExhaustion(exhaustion);
            attacker.knockback(strength, user.getX() - attacker.getX(), user.getZ() - attacker.getZ());
            attacker.hurtMarked = true;
        }
        handleBarbed(user, attacker, isParry);
    }

    // Logic for blocking a projectile
    private static void blockProjectile(Player user, Entity damageCauser, Projectile projectile, boolean isParry) {
        if (!isParry || damageCauser == null) return;
        float projectileReflectStrength = GuardingConfig.INSTANCE.projectileReflectStrength.get();
        if (projectile instanceof ProjectileAccessor accessor) accessor.setParrier(user);
        Vec3 motion = new Vec3(user.getX() - damageCauser.getX(), 0.0f, user.getZ() - damageCauser.getZ()).scale(projectileReflectStrength);
        projectile.setDeltaMovement(motion.x(), -1.5f, motion.z());
        projectile.hurtMarked = true;
    }

    // Handles all code for the barbed enchantment
    private static void handleBarbed(Player user, LivingEntity attacker, boolean isParry) {
        RandomSource random = user.getRandom();
        float damage = GuardingConfig.INSTANCE.damageAmount.get();
        float chance = GuardingConfig.INSTANCE.damageChance.get();
        int barbedLevel = EnchantmentHelper.getItemEnchantmentLevel(GuardingEnchantments.BARBED, user.getUseItem());
        if (barbedLevel <= 0) return;
        damage += isParry ? 1.0f : 0.0f; // Parrying will cause barbed to increase it's power.

        if ((random.nextFloat() <= chance && chance > 0.0f) || isParry || chance >= 1.0f) {
            attacker.hurt(attacker.damageSources().thorns(user), damage);
            user.hurtCurrentlyUsedShield(2.0f);
        }
    }

    // Determines the knockback strength on parry
    private static float getKnockbackStrength(ItemStack stack) {
        float baseStrength = GuardingConfig.INSTANCE.knockbackStrength.get();
        float additionalStrength = GuardingConfig.INSTANCE.additionalKnockbackStrengthPerLevel.get();
        int pummelingLevel = EnchantmentHelper.getItemEnchantmentLevel(GuardingEnchantments.PUMMELING, stack);
        float pummelStrength = pummelingLevel > 0 ? pummelingLevel * additionalStrength : 0.0f;

        return baseStrength + pummelStrength;
    }

    // Parry visual effects (Sound and particles)
    private static void parryEffects(Player user, Entity sourceEntity) {
        Level level = user.level();
        SoundEvent breakSound = GuardingSounds.ITEM_SHIELD_PARRY;
        level.playSound(null, user.blockPosition(), breakSound, SoundSource.PLAYERS);

        if (level instanceof ServerLevel server && sourceEntity != null) {
            server.sendParticles(GuardingParticles.PARRY, sourceEntity.getX(), sourceEntity.getEyeY(), sourceEntity.getZ(), 1, 0.0d, 0.0d, 0.0d, 0.0d);
        }
    }
}
