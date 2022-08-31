package epicmagicmod.magicmod.block;

import epicmagicmod.magicmod.fluid.ModFluidTypes;
import epicmagicmod.magicmod.fluid.fluids.ManaFluid;
import epicmagicmod.magicmod.items.ModItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidType;

public class ShardOreItem extends BlockItem {

    public final int maxDrop;
    private final int minDrop;
    public final int crushTime;
    private Item shard;
    private ManaFluid fluid;

    private final EOreType type;

    public Item getShard()
    {
        if(shard == null) {
            switch (type) {
                case Mallumon -> shard = ModItems.MALLUMON_SHARD.get();
                case Blacite -> shard = ModItems.BLACITE_SHARD.get();
                case Granizous -> shard = ModItems.GRAZINOUS_SHARD.get();
                case Torintrin -> shard = ModItems.TORINTRIN_SHARD.get();
            }
        }
        return shard;
    }

    public ManaFluid getFluid()
    {
        if(fluid == null) {
            switch (type) {
                case Mallumon -> fluid = ModFluidTypes.MALLUMON_MANA.get();
                case Blacite -> fluid = ModFluidTypes.BLACITE_MANA.get();
                case Granizous -> fluid = ModFluidTypes.GRAZINOUS_MANA.get();
                case Torintrin -> fluid = ModFluidTypes.TORINTRIN_MANA.get();
            }
        }
        return fluid;
    }


    public enum EOreType
        {
            Granizous,
            Torintrin,
            Blacite,
            Mallumon
        }



    public ShardOreItem(Block pBlock, Properties pProperties, int maxDrop, int minDrop, int crushTime, EOreType type) {
        super(pBlock, pProperties);
        this.maxDrop = maxDrop;
        this.minDrop = minDrop;
        this.crushTime = crushTime;
        this.type = type;

    }

    public int generateDrops(){
        return (RandomSource.create().nextIntBetweenInclusive(minDrop, maxDrop));

    }



}
