package epicmagicmod.magicmod.fluid.fluids;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;

public class ManaFluid extends FluidType {
    /**
     * Default constructor.
     *
     * @param properties the general properties of the fluid type
     */
    public ManaFluid(Properties properties) {
        super(properties);
    }


    @Override
    public ItemStack getBucket(FluidStack stack)
    {
        return new ItemStack(stack.getFluid().getBucket());
    }


}
