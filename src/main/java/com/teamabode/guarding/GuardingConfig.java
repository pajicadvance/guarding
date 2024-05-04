package com.teamabode.guarding;

import com.teamabode.sketch.core.api.config.Config;
import com.teamabode.sketch.core.api.config.FloatProperty;

public class GuardingConfig extends Config {
    public static final GuardingConfig INSTANCE = new GuardingConfig();

    public final FloatProperty exhaustionCost;
    public final FloatProperty knockbackStrength;
    public final FloatProperty projectileReflectStrength;
    public final FloatProperty damageAmount;
    public final FloatProperty damageChance;
    public final FloatProperty additionalKnockbackStrengthPerLevel;

    public GuardingConfig() {
        super("guarding");
        this.exhaustionCost = new FloatProperty("exhaustion_cost", 2.0f);
        this.knockbackStrength = new FloatProperty("knockback_strength", 0.5f);
        this.projectileReflectStrength = new FloatProperty("projectile_reflect_strength", 1.25f);
        this.damageAmount = new FloatProperty("damage_amount", 2.0f);
        this.damageChance = new FloatProperty("damage_chance", 0.2f);
        this.additionalKnockbackStrengthPerLevel = new FloatProperty("additional_knockback_strength_per_level", 0.15f);

        this.defineCategory("parry", this.exhaustionCost, this.knockbackStrength, this.projectileReflectStrength);
        this.defineCategory("barbed", this.damageAmount, this.damageChance);
        this.defineCategory("pummeling", this.additionalKnockbackStrengthPerLevel);
    }
}
