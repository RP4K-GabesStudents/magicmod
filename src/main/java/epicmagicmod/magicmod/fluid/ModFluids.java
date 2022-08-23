package epicmagicmod.magicmod.fluid;

import epicmagicmod.magicmod.block.ModBlocks;
import epicmagicmod.magicmod.items.ModItems;
import epicmagicmod.magicmod.main;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, main.MODID);

    // always keep with the DeferredRegister
    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);

    }

    public static final RegistryObject<FlowingFluid> SOURCE_PURPLE_MANA = FLUIDS.register("source_purple_mana", () -> new ForgeFlowingFluid.Source(ModFluids.PURPLE_MANA_PROPS));
    public static final RegistryObject<FlowingFluid> FLOWING_PURPLE_MANA = FLUIDS.register("flowing_purple_mana", () -> new ForgeFlowingFluid.Flowing(ModFluids.PURPLE_MANA_PROPS));
    public static final ForgeFlowingFluid.Properties PURPLE_MANA_PROPS = new ForgeFlowingFluid.Properties(ModFluidTypes.PURPLE_MANA, SOURCE_PURPLE_MANA, FLOWING_PURPLE_MANA).bucket(ModItems.PURPLE_MANA_BUCKET).slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.PURPLE_SOURCE_BLOCK);

}
