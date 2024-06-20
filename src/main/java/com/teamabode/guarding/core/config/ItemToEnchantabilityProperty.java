package com.teamabode.guarding.core.config;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teamabode.guarding.Guarding;
import com.teamabode.sketch.core.api.config.ConfigProperty;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

// WIP
public class ItemToEnchantabilityProperty extends ConfigProperty<Map<ResourceLocation, Integer>> {

    public ItemToEnchantabilityProperty(String name, Map<ResourceLocation, Integer> defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public Map<ResourceLocation, Integer> get() {
        return (Map<ResourceLocation, Integer>) this.value;
    }

    @Override
    public JsonElement toJson() {
        JsonObject root = new JsonObject();
        this.get().forEach((location, integer) -> {
            root.addProperty(location.toString(), integer);
        });
        return root;
    }

    @Override
    public Map<ResourceLocation, Integer> fromJson(JsonElement element) {
        ImmutableMap.Builder<ResourceLocation, Integer> builder = new ImmutableMap.Builder<>();
        JsonObject root = element.getAsJsonObject();

        for (var entry : root.entrySet()) {
            ResourceLocation location = ResourceLocation.tryParse(entry.getKey());

            if (location == null) {
                Guarding.LOGGER.warn("{} is an invalid resource location", entry.getKey());
                continue;
            }
            if (!entry.getValue().isJsonPrimitive() || !entry.getValue().getAsJsonPrimitive().isNumber()) {
                Guarding.LOGGER.warn("{} is not an integer", entry.getValue().getAsInt());
            }
            int enchantability = entry.getValue().getAsInt();
            builder.put(location, enchantability);
        }
        return builder.build();
    }
}
