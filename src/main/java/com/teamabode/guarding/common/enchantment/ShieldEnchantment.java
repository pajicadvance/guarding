package com.teamabode.guarding.common.enchantment;

import com.teamabode.guarding.Guarding;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class ShieldEnchantment extends Enchantment {
    public ShieldEnchantment(int weight, int maxLevel, Cost minCost, Cost maxCost, int anvilCost) {
        super(Enchantment.definition(Guarding.SHIELD_ENCHANTABLE, weight, maxLevel, minCost, maxCost, anvilCost, EquipmentSlot.MAINHAND));
    }
}
