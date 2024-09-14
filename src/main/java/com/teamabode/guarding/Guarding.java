package com.teamabode.guarding;

import com.teamabode.guarding.core.registry.*;
import com.teamabode.guarding.core.util.ShieldUtils;
import com.teamabode.sketch.core.api.config.ConfigManager;
import com.teamabode.sketch.core.api.event.ShieldEvents;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Guarding implements ModInitializer {
    public static final String MOD_ID = "guarding";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitialize() {
        GuardingItems.init();
        GuardingEnchantmentEffects.init();
        GuardingSounds.init();
        GuardingParticles.init();
        GuardingCritieriaTriggers.init();
        GuardingRecipeSerializers.init();
        GuardingStats.init();


        ShieldEvents.BLOCKED.register(ShieldUtils::onBlocked);
        ConfigManager.INSTANCE.register(GuardingConfig.INSTANCE);
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
