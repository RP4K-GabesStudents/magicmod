package epicmagicmod.magicmod.fluid;

import com.mojang.math.Vector3f;
import epicmagicmod.magicmod.fluid.fluids.ManaFluid;
import net.minecraft.resources.ResourceLocation;
import epicmagicmod.magicmod.main;
import net.minecraft.util.FastColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.logging.Logger;

public class ModFluidTypes {

    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING = new ResourceLocation("block/water_flow");

    public static final ResourceLocation MANA_OVERLAY = new ResourceLocation(main.MODID, "misc/mana_water");
    public static final DeferredRegister<FluidType>FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, main.MODID);


    public static void register(IEventBus eventBus){
        FLUID_TYPES.register(eventBus);

    }

    private static RegistryObject<ManaFluid>register(String name, FluidType.Properties properties, Vector3f color, int id){

        int col = FastColor.ARGB32.color(255, (int)(color.x() * 255), (int)(color.y() * 255), (int)(color.z() * 255));
        Logger.getAnonymousLogger().info("Test: A " + col);
        return FLUID_TYPES.register(name, ()-> new ManaFluid(WATER_STILL, WATER_FLOWING, MANA_OVERLAY, col, color, properties, id));

    }

    public static final RegistryObject<ManaFluid>GRAZINOUS_MANA = register("grazinous_mana_fluid", FluidType.Properties.create().canDrown(false).canHydrate(true).motionScale(0.005).canConvertToSource(false),new Vector3f(0.6f, 0.853f, 1f),0);
    public static final RegistryObject<ManaFluid>TORINTRIN_MANA = register("torintrin_mana_fluid", FluidType.Properties.create().canDrown(true).canHydrate(false).motionScale(0.05).canConvertToSource(false),new Vector3f(0.99f, 0.98f, 0.386f),1);
    public static final RegistryObject<ManaFluid>BLACITE_MANA = register("blacite_mana_fluid", FluidType.Properties.create().canDrown(true).canHydrate(false).motionScale(0.005).canExtinguish(false).canConvertToSource(false),new Vector3f(0.66f, 0f, 0),2);
    public static final RegistryObject<ManaFluid>MALLUMON_MANA = register("mallumon_mana_fluid", FluidType.Properties.create().canDrown(true).canSwim(false).canExtinguish(false).canHydrate(false).canConvertToSource(false),new Vector3f(0.511f, 0f, 0.73f),3);


}
