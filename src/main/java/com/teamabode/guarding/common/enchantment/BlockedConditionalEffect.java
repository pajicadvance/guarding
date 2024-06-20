package com.teamabode.guarding.common.enchantment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

public record BlockedConditionalEffect<T>(EnchantmentTarget affected, T effect, boolean cancelOnParry, Optional<LootItemCondition> requirements) {

    public static <S> Codec<BlockedConditionalEffect<S>> parriedCodec(Codec<S> codec, LootContextParamSet contextSet) {
        return RecordCodecBuilder.create(instance -> instance.group(
                EnchantmentTarget.CODEC.fieldOf("affected").forGetter(BlockedConditionalEffect::affected),
                codec.fieldOf("effect").forGetter(BlockedConditionalEffect::effect),
                ConditionalEffect.conditionCodec(contextSet).optionalFieldOf("requirements").forGetter(BlockedConditionalEffect::requirements)
        ).apply(instance, (affected, effect, requirements) -> new BlockedConditionalEffect<>(affected, effect, false, requirements)));
    }

    public static <S> Codec<BlockedConditionalEffect<S>> codec(Codec<S> codec, LootContextParamSet contextSet) {
        return RecordCodecBuilder.create(instance -> instance.group(
                EnchantmentTarget.CODEC.fieldOf("affected").forGetter(BlockedConditionalEffect::affected),
                codec.fieldOf("effect").forGetter(BlockedConditionalEffect::effect),
                Codec.BOOL.fieldOf("cancel_on_parry").forGetter(BlockedConditionalEffect::cancelOnParry),
                ConditionalEffect.conditionCodec(contextSet).optionalFieldOf("requirements").forGetter(BlockedConditionalEffect::requirements)
        ).apply(instance, BlockedConditionalEffect::new));
    }

    public boolean matches(LootContext lootContext) {
        return this.requirements.map(lootItemCondition -> lootItemCondition.test(lootContext)).orElse(true);
    }
}
