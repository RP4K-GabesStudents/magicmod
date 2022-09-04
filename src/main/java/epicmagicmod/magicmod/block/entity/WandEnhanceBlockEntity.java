package epicmagicmod.magicmod.block.entity;

import epicmagicmod.magicmod.block.ShardOreItem;
import epicmagicmod.magicmod.items.ModItems;
import epicmagicmod.magicmod.items.utilities.ManaBucket;
import epicmagicmod.magicmod.items.wands.WandParent;
import epicmagicmod.magicmod.screen.WandEnhanceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

public class WandEnhanceBlockEntity extends BlockEntity implements MenuProvider {

    private int progress;
    private int maxProgress = 240;
    //Stages: 160 fill time, 240 finish
    private final ContainerData data;
    // Box 0 = Wand
    // Box 1 -3 = Wand Catalysts
    // Box 4,6,8 Wand Dependent shards
    // Box 5, 7 Wand Dependent Mana Buckets
    private final ItemStackHandler itemHandler = new ItemStackHandler(9){
        @Override
        protected void onContentsChanged(int slot) {

            switch (slot)
            {
                case 0:
                    if(itemHandler.getStackInSlot(0).getItem() instanceof ShardOreItem so)
                    {
                        maxProgress = so.crushTime;
                    }
                    break;
            }


            setChanged();
        }
    };
    private LazyOptional<IItemHandler>lazyItemHandler = LazyOptional.empty();


    public WandEnhanceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.WAND_ENHANCER.get(), pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                int val = 0;
                switch (pIndex) {

                    case 0 -> val = WandEnhanceBlockEntity.this.progress;

                    case 1 -> val = WandEnhanceBlockEntity.this.maxProgress;

                    case 2 -> val = getFluidColor();

                    case 3 -> {
                        if (itemHandler.getStackInSlot(0).getItem() instanceof WandParent)
                            val = 1;
                    }
                        //4,5,6,7,8,9,10,11
                        default ->
                        {
                            if(itemHandler.getStackInSlot(0).getItem() instanceof WandParent wp) {
                                Item item = itemHandler.getStackInSlot(pIndex-3).getItem();
                                if (item == Items.AIR) {
                                    break;
                                }
                                if(pIndex < 7 && item == ModItems.WANDCORE.get())
                                {
                                    val = 1;
                                    break;
                                }

                                if(pIndex % 2 == 1) {
                                    if (item == wp.getShard()) {
                                        val = 1;
                                        break;
                                    }
                                }
                                else
                                {
                                    if (item instanceof ManaBucket mb && mb.getFluid().getFluidType() == wp.getFluid()) {
                                        val = 1;
                                        break;
                                    }
                                }
                                val = -1;
                            }
                        }
                    }
                return val;

            }

            @Override
            public void set(int pIndex, int pValue) {
                Logger.getAnonymousLogger().info("The impossible happened");
                //This literally never happens
                /*
                switch (pIndex){

                    case 0 -> ManaExtractorBlockEntity.this.progress = pValue;

                    case 1 -> ManaExtractorBlockEntity.this.maxProgress = pValue;

                    case 2 -> {
                        ManaExtractorBlockEntity.this.milliBuckets = pValue;

                    }
                    case 4 -> setFluid(pValue);
                }*/
            }

            @Override
            public int getCount() {
                return 12;
            }

        };
    }


    @Override
    public Component getDisplayName() {
        return Component.literal("Wand Altar");
    }



    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new WandEnhanceMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(()-> itemHandler);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){

            return lazyItemHandler.cast();

        }
        return super.getCapability(cap);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {

        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("progress", progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("progress");

        if(itemHandler.getStackInSlot(0).getItem() instanceof ShardOreItem so)
        {
            maxProgress = so.crushTime;
        }
    }

    private int getFluidColor()
    {
        if(itemHandler.getStackInSlot(0).getItem() instanceof WandParent wand) {
            return wand.getFluid().tintColor;
        }
        return -1;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockstate, WandEnhanceBlockEntity blockEntity){


        if(level.isClientSide())
            return;
        if (canProgress(blockEntity)){

            if (blockEntity.progress++ >= blockEntity.maxProgress){
                //Has to be here to prevent exploitation... :/

                complete(blockEntity);
            }
        }
        else {
            blockEntity.progress = 0;
        }
        setChanged(level, blockPos, blockstate);

    }

    private static boolean canProgress(WandEnhanceBlockEntity blockEntity) {
        if (blockEntity.itemHandler.getStackInSlot(0).getItem() instanceof WandParent wand) {

            for (int i = 1; i < 4; i++) {
                if (blockEntity.itemHandler.getStackInSlot(i).getItem() != ModItems.WANDCORE.get()) {
                    return false;
                }
            }

            for (int i = 4; i < 9; i++) {
                if (i % 2 == 1) {
                    if (!(blockEntity.itemHandler.getStackInSlot(i).getItem() instanceof ManaBucket mb && mb.getFluid().getFluidType() == wand.getFluid())) {
                        return false;
                    }
                } else {
                    if (blockEntity.itemHandler.getStackInSlot(i).getItem() != wand.getShard()) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    private static void complete(WandEnhanceBlockEntity blockEntity) {

        blockEntity.progress = 0;

        for(int i = 1; i < 9; i++)
        {
            if(RandomSource.create().nextInt(100) > 4)
                blockEntity.itemHandler.getStackInSlot(i).shrink(1);
        }
        ((WandParent) blockEntity.itemHandler.getStackInSlot(0).getItem()).LevelUpWand(blockEntity.itemHandler.getStackInSlot(0));
    }
}
