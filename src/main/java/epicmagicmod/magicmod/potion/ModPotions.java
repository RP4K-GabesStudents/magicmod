package epicmagicmod.magicmod.potion;

import epicmagicmod.magicmod.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import epicmagicmod.magicmod.main;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, main.MODID);

    public static final RegistryObject<Potion> MANA_MODIFY_POTION = POTIONS.register("mana_modify_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANA_MODIFY_EFFECT.get(), 900, 0)));
    public static final RegistryObject<Potion> MANA_MODIFY_POTION_2 = POTIONS.register("mana_modify_potion_2",
            () -> new Potion(new MobEffectInstance(ModEffects.MANA_MODIFY_EFFECT.get(), 600, 1)));
    public static final RegistryObject<Potion> SPEEDY_POTION = POTIONS.register("speedy_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANA_CAP_EFFECT.get(), 12000, 0)));
    public static final RegistryObject<Potion> SPEEDY_POTION_2 = POTIONS.register("speedy_potion_2",
            () -> new Potion(new MobEffectInstance(ModEffects.MANA_CAP_EFFECT.get(), 9000, 1)));
    public static final RegistryObject<Potion> DEATH_AURA_POTION = POTIONS.register("death_aura_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DEATH_AURA_EFFECT.get(), 900, 0)));
    public static final RegistryObject<Potion> DEATH_AURA_POTION_2 = POTIONS.register("death_aura_potion_2",
            () -> new Potion(new MobEffectInstance(ModEffects.DEATH_AURA_EFFECT.get(), 400, 1)));

    public static final RegistryObject<Potion> FIRE_POTION = POTIONS.register("fire_aura_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.FIRE_EFFECT.get(), 900, 0)));
    public static final RegistryObject<Potion> FIRE_POTION_2 = POTIONS.register("fire_aura_potion_2",
            () -> new Potion(new MobEffectInstance(ModEffects.FIRE_EFFECT.get(), 400, 1)));


    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}



