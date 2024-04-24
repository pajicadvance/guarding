package com.teamabode.guarding.common.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;

public class BarbedEnchantment extends ShieldEnchantment {
    public BarbedEnchantment() {
        super(2, 1, Enchantment.constantCost(20), Enchantment.constantCost(50), 2);
    }
}
