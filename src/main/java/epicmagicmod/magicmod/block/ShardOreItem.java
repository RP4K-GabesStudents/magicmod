package epicmagicmod.magicmod.block;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidType;

public class ShardOreItem extends BlockItem {

    public final int maxDrop;
    private final int minDrop;
    public final Item shard;
    public final FluidType fluid;



    public ShardOreItem(Block pBlock, Properties pProperties, int maxDrop, int minDrop, Item shard, FluidType fluid) {
        super(pBlock, pProperties);
        this.maxDrop = maxDrop;
        this.minDrop = minDrop;
        this.shard = shard;
        this.fluid = fluid;
    }

    public int generateDrops(){

        return (RandomSource.create().nextInt(minDrop, maxDrop));

    }



}
