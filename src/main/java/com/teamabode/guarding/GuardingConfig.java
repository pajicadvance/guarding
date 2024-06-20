package com.teamabode.guarding;

import com.teamabode.sketch.core.api.config.Config;
import com.teamabode.sketch.core.api.config.FloatProperty;
import com.teamabode.sketch.core.api.config.IntProperty;

// TODO: Item to int map property which allows custom shields to be given enchantibility.
public class GuardingConfig extends Config {
    public static final GuardingConfig INSTANCE = new GuardingConfig();

    public final FloatProperty exhaustionCost;
    public final FloatProperty knockbackStrength;
    public final IntProperty shieldEnchantibility;
    public final IntProperty netheriteShieldEnchantibility;

    public GuardingConfig() {
        super("guarding");
        this.exhaustionCost = new FloatProperty("exhaustion_cost", 2.0f);
        this.knockbackStrength = new FloatProperty("knockback_strength", 0.5f);
        this.shieldEnchantibility = new IntProperty("minecraft:shield", 9);
        this.netheriteShieldEnchantibility = new IntProperty("guarding:netherite_shield", 15);

        this.defineCategory("parry", this.exhaustionCost, this.knockbackStrength);
        this.defineCategory("enchantability", this.shieldEnchantibility, this.netheriteShieldEnchantibility);
    }
}
