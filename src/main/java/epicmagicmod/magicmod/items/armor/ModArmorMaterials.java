package epicmagicmod.magicmod.items.armor;


import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {


    ADEPT("magicmod:adeptarmor", 5, new int[]{1, 2, 3, 1}, 25, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, new int [] {100, 100, 200, 100}, new int [] {5000, 12500, 25000, 7500
    },
            () -> {
        return Ingredient.of(Items.LEATHER);
    }),
    EMPOWERED("magicmod:empoweredarmor", 8, new int[]{3, 6, 9, 3}, 75, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.4F, 0.4F, new int [] {1000, 1000, 2000, 1000}, new int [] {50000, 125000, 250000, 75000
    },
            () -> {
                return Ingredient.of(Items.DIAMOND);
    });




    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    public final int [] MANA_REGEN_BONUS;
    public final int [] MANA_CAP_INCREASE;

    private ModArmorMaterials(String pName, int pDurabilityMultiplier, int[] pSlotProtections, int pEnchantmentValue, SoundEvent pSound, float pToughness, float pKnockbackResistance, int [] manaRegenBonus, int [] manaCapIncrease, Supplier<Ingredient> pRepairIngredient) {
        this.name = pName;
        this.durabilityMultiplier = pDurabilityMultiplier;
        this.slotProtections = pSlotProtections;
        this.enchantmentValue = pEnchantmentValue;
        this.sound = pSound;
        this.toughness = pToughness;
        this.knockbackResistance = pKnockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(pRepairIngredient);

        MANA_REGEN_BONUS = manaRegenBonus;
        MANA_CAP_INCREASE = manaCapIncrease;
    }

    public int getDurabilityForSlot(EquipmentSlot pSlot) {
        return HEALTH_PER_SLOT[pSlot.getIndex()] * this.durabilityMultiplier;
    }

    public int getDefenseForSlot(EquipmentSlot pSlot) {
        return this.slotProtections[pSlot.getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    /**
     * Gets the percentage of knockback resistance provided by armor of the material.
     */
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }


}
