package epicmagicmod.magicmod.block;

import epicmagicmod.magicmod.fluid.ModFluidTypes;
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
    private FluidType fluid;

    private EOreType type;

    public Item getShard()
    {
        if(shard == null) {
            switch (type) {
                case Mallumon -> {
                    shard = ModItems.BLACITE.get();
                }
                case Blacite -> {
                    shard = ModItems.BLACITE.get();
                }
                case Granizous -> {
                    shard = ModItems.BLACITE.get();
                }
                case Torintrin -> {
                    shard = ModItems.BLACITE.get();
                }
            }
        }
        return shard;
    }

    public FluidType getFluid()
    {
        if(fluid == null) {
            switch (type) {
                case Mallumon -> {
                    fluid = ModFluidTypes.PURPLE_MANA.get();
                }
                case Blacite -> {
                    fluid = ModFluidTypes.PURPLE_MANA.get();
                }
                case Granizous -> {
                    fluid = ModFluidTypes.PURPLE_MANA.get();
                }
                case Torintrin -> {
                    fluid = ModFluidTypes.PURPLE_MANA.get();
                }
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
