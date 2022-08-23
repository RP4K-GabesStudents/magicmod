package epicmagicmod.magicmod.fluid;

import com.mojang.math.Vector3f;
import epicmagicmod.magicmod.fluid.fluids.ManaFluid;
import net.minecraft.resources.ResourceLocation;
import epicmagicmod.magicmod.main;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class ModFluidTypes {

    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING = new ResourceLocation("block/water_flow");

    public static final ResourceLocation MANA_OVERLAY = new ResourceLocation(main.MODID, "misc/mana_water");
    public static final DeferredRegister<FluidType>FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, main.MODID);


    public static void register(IEventBus eventBus){
        FLUID_TYPES.register(eventBus);

    }

    private static RegistryObject<FluidType>register(String name, FluidType.Properties properties, Vector3f color){

        return FLUID_TYPES.register(name, ()-> new ManaFluid(WATER_STILL, WATER_FLOWING, MANA_OVERLAY, 0xA1E038D0, color, properties));

    }

    public static final RegistryObject<FluidType>PURPLE_MANA = register("mana_gain_fluid", FluidType.Properties.create().lightLevel(10).canDrown(false).canConvertToSource(false),new Vector3f(0.00f, 0.767f, 1.00f));


}
