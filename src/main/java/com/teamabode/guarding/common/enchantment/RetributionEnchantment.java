package com.teamabode.guarding.common.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;

public class RetributionEnchantment extends GuardingEnchantment {

    public RetributionEnchantment() {
        super(2, 2, Enchantment.dynamicCost(10, 25), Enchantment.dynamicCost(55, 25), 4);
    }

    public boolean isTreasureOnly() {
        return false;
        //return Guarding.CONFIG.getGroup("retribution").getBooleanProperty("is_treasure");
    }

    protected boolean checkCompatibility(Enchantment other) {
        return !(other instanceof BarbedEnchantment);
    }
}
