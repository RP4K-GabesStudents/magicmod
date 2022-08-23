package epicmagicmod.magicmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import epicmagicmod.magicmod.main;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, main.MODID);

    public static final RegistryObject<MobEffect> MANA_MODIFY_EFFECT = MOB_EFFECTS.register("mana_modify_effect",
            () -> new ManaModifyEffect(MobEffectCategory.BENEFICIAL, 10371829));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
