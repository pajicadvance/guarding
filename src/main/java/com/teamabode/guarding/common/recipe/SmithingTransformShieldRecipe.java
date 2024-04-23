package com.teamabode.guarding.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabode.guarding.Guarding;
import com.teamabode.guarding.core.init.GuardingRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;

import java.util.stream.Stream;

public record SmithingTransformShieldRecipe(Ingredient template, Ingredient base, Ingredient addition, ItemStack result) implements SmithingRecipe {
    public static final MapCodec<SmithingTransformShieldRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("template").forGetter(SmithingTransformShieldRecipe::template),
            Ingredient.CODEC.fieldOf("base").forGetter(SmithingTransformShieldRecipe::base),
            Ingredient.CODEC.fieldOf("addition").forGetter(SmithingTransformShieldRecipe::addition),
            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(SmithingTransformShieldRecipe::result)
    ).apply(instance, SmithingTransformShieldRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SmithingTransformShieldRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

    public SmithingTransformShieldRecipe(Ingredient template, Ingredient base, Ingredient addition, ItemStack result) {
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.template.test(container.getItem(0)) && this.base.test(container.getItem(1)) && this.addition.test(container.getItem(2));
    }

    @Override
    public ItemStack assemble(Container container, HolderLookup.Provider provider) {
        ItemStack itemStack = container.getItem(1).transmuteCopy(this.result.getItem(), this.result.getCount());

        if (itemStack.has(DataComponents.BASE_COLOR)) {
            itemStack.remove(DataComponents.BASE_COLOR);
        }
        if (itemStack.has(DataComponents.BANNER_PATTERNS)) {
            itemStack.remove(DataComponents.BANNER_PATTERNS);
        }
        itemStack.applyComponents(this.result.getComponentsPatch());
        return itemStack;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        return this.template.test(stack);
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return this.base.test(stack);
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return this.addition.test(stack);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return GuardingRecipeSerializers.SMITHING_TRANSFORM_SHIELD;
    }

    @Override
    public boolean isIncomplete() {
        return Stream.of(this.template, this.base, this.addition).anyMatch(Ingredient::isEmpty);
    }

    public static class Serializer implements RecipeSerializer<SmithingTransformShieldRecipe> {
        @Override
        public MapCodec<SmithingTransformShieldRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SmithingTransformShieldRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static SmithingTransformShieldRecipe fromNetwork(RegistryFriendlyByteBuf packet) {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(packet);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(packet);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(packet);
            ItemStack result = ItemStack.STREAM_CODEC.decode(packet);
            return new SmithingTransformShieldRecipe(template, base, addition, result);
        }

        public static void toNetwork(RegistryFriendlyByteBuf packet, SmithingTransformShieldRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(packet, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(packet, recipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(packet, recipe.addition);
            ItemStack.STREAM_CODEC.encode(packet, recipe.result);
        }
    }
}
