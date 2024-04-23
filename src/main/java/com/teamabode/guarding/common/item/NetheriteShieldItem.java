package com.teamabode.guarding.common.item;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.teamabode.guarding.core.init.GuardingSounds;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.UUID;
import java.util.function.Supplier;

public class NetheriteShieldItem extends ShieldItem {
    private static final UUID MODIIFER_UUID = UUID.fromString("8b128327-f878-4e94-ada2-707cd81b13af");
    private final Supplier<ItemAttributeModifiers> defaultModifiers;

    public NetheriteShieldItem(Properties properties) {
        super(properties);

        this.defaultModifiers = Suppliers.memoize(() -> {
           ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
           builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(MODIIFER_UUID, "Shield modifier", 0.1d, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.OFFHAND);
           return builder.build();
        });
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return this.defaultModifiers.get();
    }

    public int getEnchantmentValue() {
        return 15;
    }

    public Holder<SoundEvent> getEquipSound() {
        return GuardingSounds.ITEM_NETHERITE_SHIELD_EQUIP;
    }

    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(Items.NETHERITE_INGOT);
    }
}
