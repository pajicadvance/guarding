package com.teamabode.guarding;

import com.teamabode.guarding.core.init.*;
import com.teamabode.sketch.core.api.config.Config;
import com.teamabode.sketch.core.api.config.ConfigBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Guarding implements ModInitializer {
    public static final String MOD_ID = "guarding";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final TagKey<Item> SHIELD_ENCHANTABLE = TagKey.create(Registries.ITEM, id("enchantable/shield"));

    public static final Config CONFIG = new ConfigBuilder(MOD_ID)
            .addGroup("general", builder -> {
                builder.addBooleanProperty("no_shield_block_delay", true);
                return builder;
            })
            .addGroup("parry", builder -> {
                builder.addFloatProperty("exhaustion_cost", 2.0f);
                builder.addFloatProperty("knockback_strength", 0.5f);
                builder.addFloatProperty("projectile_launch_strength", 1.25f);
                return builder;
            })
            .addGroup("barbed", builder -> {
                builder.addFloatProperty("damage_amount", 2.0f);
                builder.addFloatProperty("damage_chance", 0.2f);
                return builder;
            })
            .addGroup("pummeling", builder -> {
                builder.addFloatProperty("additional_knockback_strength_per_level", 0.15f);
                return builder;
            })
            /*
            .addGroup("retribution", builder -> {
                builder.addIntProperty("slowness_amplifier", 1);
                builder.addBooleanProperty("is_treasure", true);
                return builder;
            })
            */
            .build();

    public void onInitialize() {
        GuardingItems.init();
        GuardingEnchantments.init();
        GuardingSounds.init();
        GuardingParticles.init();
        GuardingCallbacks.init();
        GuardingCritieriaTriggers.init();
        GuardingRecipeSerializers.init();
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
