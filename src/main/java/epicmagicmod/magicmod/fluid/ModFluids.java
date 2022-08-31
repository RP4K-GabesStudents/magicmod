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

    public static final RegistryObject<FlowingFluid> SOURCE_GRAZINOUS_MANA = FLUIDS.register("source_grazinous_mana", () -> new ForgeFlowingFluid.Source(ModFluids.GRAZINOUS_MANA_PROPS));
    public static final RegistryObject<FlowingFluid> FLOWING_GRAZINOUS_MANA = FLUIDS.register("flowing_grazinous_mana", () -> new ForgeFlowingFluid.Flowing(ModFluids.GRAZINOUS_MANA_PROPS));
    public static final ForgeFlowingFluid.Properties GRAZINOUS_MANA_PROPS = new ForgeFlowingFluid.Properties(ModFluidTypes.GRAZINOUS_MANA, SOURCE_GRAZINOUS_MANA, FLOWING_GRAZINOUS_MANA).bucket(ModItems.GRAZINOUS_MANA_BUCKET).slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.GRAZINOUS_SOURCE_BLOCK);

    public static final RegistryObject<FlowingFluid> SOURCE_TORINTRIN_MANA = FLUIDS.register("source_torintrin_mana", () -> new ForgeFlowingFluid.Source(ModFluids.TORINTRIN_MANA_PROPS));
    public static final RegistryObject<FlowingFluid> FLOWING_TORINTRIN_MANA = FLUIDS.register("flowing_torintrin_mana", () -> new ForgeFlowingFluid.Flowing(ModFluids.TORINTRIN_MANA_PROPS));
    public static final ForgeFlowingFluid.Properties TORINTRIN_MANA_PROPS = new ForgeFlowingFluid.Properties(ModFluidTypes.TORINTRIN_MANA, SOURCE_TORINTRIN_MANA, FLOWING_TORINTRIN_MANA).bucket(ModItems.TORINTRIN_MANA_BUCKET).slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.TORINTRIN_SOURCE_BLOCK);

    public static final RegistryObject<FlowingFluid> SOURCE_BLACITE_MANA = FLUIDS.register("source_blacite_mana", () -> new ForgeFlowingFluid.Source(ModFluids.BLACITE_MANA_PROPS));
    public static final RegistryObject<FlowingFluid> FLOWING_BLACITE_MANA = FLUIDS.register("flowing_blacite_mana", () -> new ForgeFlowingFluid.Flowing(ModFluids.BLACITE_MANA_PROPS));
    public static final ForgeFlowingFluid.Properties BLACITE_MANA_PROPS = new ForgeFlowingFluid.Properties(ModFluidTypes.BLACITE_MANA, SOURCE_BLACITE_MANA, FLOWING_BLACITE_MANA).bucket(ModItems.BLACITE_MANA_BUCKET).slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.BLACITE_SOURCE_BLOCK);

    public static final RegistryObject<FlowingFluid> SOURCE_MALLUMON_MANA = FLUIDS.register("source_mallumon_mana", () -> new ForgeFlowingFluid.Source(ModFluids.MALLUMON_MANA_PROPS));
    public static final RegistryObject<FlowingFluid> FLOWING_MALLUMON_MANA = FLUIDS.register("flowing_mallumon_mana", () -> new ForgeFlowingFluid.Flowing(ModFluids.MALLUMON_MANA_PROPS));
    public static final ForgeFlowingFluid.Properties MALLUMON_MANA_PROPS = new ForgeFlowingFluid.Properties(ModFluidTypes.MALLUMON_MANA, SOURCE_MALLUMON_MANA, FLOWING_MALLUMON_MANA).bucket(ModItems.MALLUMON_MANA_BUCKET).slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.MALLUMON_SOURCE_BLOCK);

}
