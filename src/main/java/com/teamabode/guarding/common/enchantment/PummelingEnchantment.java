package com.teamabode.guarding.common.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;

public class PummelingEnchantment extends ShieldEnchantment {

    public PummelingEnchantment() {
        super(5, 3, Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(60, 10), 4);
    }
}
