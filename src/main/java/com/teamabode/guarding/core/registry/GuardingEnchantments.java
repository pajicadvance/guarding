package com.teamabode.guarding.core.registry;

import com.teamabode.guarding.Guarding;
import com.teamabode.guarding.common.enchantment.BarbedEnchantment;
import com.teamabode.guarding.common.enchantment.PummelingEnchantment;
import com.teamabode.guarding.common.enchantment.RetributionEnchantment;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public class GuardingEnchantments {
    public static final Enchantment BARBED = register("barbed", new BarbedEnchantment());
    public static final Enchantment PUMMELING = register("pummeling", new PummelingEnchantment());
    public static final Enchantment RETRIBUTION = register("retribution", new RetributionEnchantment());

    private static <E extends Enchantment> E register(String name, E enchantment) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT, new ResourceLocation(Guarding.MOD_ID, name), enchantment);
    }

    public static void init() {

    }
}
