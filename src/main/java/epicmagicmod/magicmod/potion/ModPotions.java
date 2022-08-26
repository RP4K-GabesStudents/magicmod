package epicmagicmod.magicmod.potion;

import epicmagicmod.magicmod.effect.ModEffects;
import epicmagicmod.magicmod.items.ModCreativeModeTab;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
            () -> new Potion(new MobEffectInstance(ModEffects.MANA_MODIFY_EFFECT.get(), 420, 1)));


    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}



