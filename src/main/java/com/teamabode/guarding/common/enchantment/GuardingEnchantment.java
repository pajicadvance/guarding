package com.teamabode.guarding.common.enchantment;

import com.teamabode.guarding.Guarding;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;

public class GuardingEnchantment extends Enchantment {

    public GuardingEnchantment(int weight, int maxLevel, Cost minCost, Cost maxCost, int anvilCost) {
        super(Enchantment.definition(Guarding.SHIELD_ENCHANTABLE, weight, maxLevel, minCost, maxCost, anvilCost, EquipmentSlot.MAINHAND));
    }
}
