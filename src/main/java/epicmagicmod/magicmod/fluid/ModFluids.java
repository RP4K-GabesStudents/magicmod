package epicmagicmod.magicmod.fluid;

import epicmagicmod.magicmod.main;
import net.minecraft.core.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFluids {

    public static final DeferredRegister<FluidType> FLUIDS = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, main.MODID);

    // always keep with the DeferredRegister
    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);

    }



}
