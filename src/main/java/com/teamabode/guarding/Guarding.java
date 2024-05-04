package com.teamabode.guarding;

import com.teamabode.guarding.core.init.*;
import com.teamabode.sketch.core.api.config.ConfigManager;
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

    public void onInitialize() {
        GuardingItems.init();
        GuardingEnchantments.init();
        GuardingSounds.init();
        GuardingParticles.init();
        GuardingCallbacks.init();
        GuardingCritieriaTriggers.init();
        GuardingRecipeSerializers.init();
        ConfigManager.INSTANCE.register(GuardingConfig.INSTANCE);
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
