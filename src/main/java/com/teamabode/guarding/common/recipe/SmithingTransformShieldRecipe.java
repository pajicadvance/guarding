package com.teamabode.guarding.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabode.guarding.Guarding;
import com.teamabode.guarding.core.init.GuardingRecipeSerializers;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;

import java.util.stream.Stream;

public record SmithingTransformShieldRecipe(Ingredient template, Ingredient base, Ingredient addition, ItemStack result) implements SmithingRecipe {
    public static final Codec<SmithingTransformShieldRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.CODEC.fieldOf("template").forGetter(SmithingTransformShieldRecipe::template),
            Ingredient.CODEC.fieldOf("base").forGetter(SmithingTransformShieldRecipe::base),
            Ingredient.CODEC.fieldOf("addition").forGetter(SmithingTransformShieldRecipe::addition),
            ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(SmithingTransformShieldRecipe::result)
    ).apply(instance, SmithingTransformShieldRecipe::new));

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
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack itemStack = this.result.copy();
        CompoundTag compoundTag = container.getItem(1).getTag();
        if (compoundTag != null) {
            itemStack.setTag(compoundTag.copy());
            itemStack.removeTagKey("BlockEntityTag");
        }
        return itemStack;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
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
        public Codec<SmithingTransformShieldRecipe> codec() {
            return SmithingTransformShieldRecipe.CODEC;
        }

        @Override
        public SmithingTransformShieldRecipe fromNetwork(FriendlyByteBuf packet) {
            Ingredient template = Ingredient.fromNetwork(packet);
            Ingredient base = Ingredient.fromNetwork(packet);
            Ingredient addition = Ingredient.fromNetwork(packet);
            ItemStack result = packet.readItem();
            return new SmithingTransformShieldRecipe(template, base, addition, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf packet, SmithingTransformShieldRecipe recipe) {
            recipe.template.toNetwork(packet);
            recipe.base.toNetwork(packet);
            recipe.addition.toNetwork(packet);
            packet.writeItem(recipe.result);
        }
    }
}
