package epicmagicmod.magicmod.block.entity;

import epicmagicmod.magicmod.block.ShardOreItem;
import epicmagicmod.magicmod.fluid.ModFluidTypes;
import epicmagicmod.magicmod.items.ModItems;
import epicmagicmod.magicmod.screen.ManaExtractionMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

public class ManaExtractorBlockEntity extends BlockEntity implements MenuProvider {

    private int progress;
    private int maxProgress = 9999999;
    private int milliBuckets;
    private final int maxMilliBuckets = 4000;
    private FluidType manaType;
    private final ContainerData data;
    // Box 0 = Ore input(s)
    // Box 1 = Input empty bucket(s)
    // Box 2 - 4 = Output shard(s)
    // Box 5 = Output Mana Bucket(s)
    private final ItemStackHandler itemHandler = new ItemStackHandler(6){
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



    public ManaExtractorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MANA_EXTRACTOR.get(), pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){

                    case 0 -> ManaExtractorBlockEntity.this.progress;

                    case 1 -> ManaExtractorBlockEntity.this.maxProgress;

                    case 2 -> ManaExtractorBlockEntity.this.milliBuckets;

                    case 4 -> ManaExtractorBlockEntity.this.maxMilliBuckets;

                    case 3 -> getFluid();

                    default -> 0;
                };

            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){

                    case 0 -> ManaExtractorBlockEntity.this.progress = pValue;

                    case 1 -> ManaExtractorBlockEntity.this.maxProgress = pValue;

                    case 2 -> ManaExtractorBlockEntity.this.milliBuckets = pValue;

                    case 3 -> setFluid(pValue);

                }

            }

            @Override
            public int getCount() {

                return 6;
            }
        };
    }


    @Override
    public Component getDisplayName() {
        return Component.literal("Mana Extractor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ManaExtractionMenu(pContainerId, pPlayerInventory, this, this.data);
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
        pTag.putInt("millibuckets", milliBuckets);
        pTag.putInt("fluid_type", getFluid());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("progress");
        milliBuckets = pTag.getInt("millibuckets");
        setFluid(pTag.getInt("fluid_type"));
    }

    private void setFluid(int id){

         switch (id){

            case 0 -> manaType = ModFluidTypes.PURPLE_MANA.get();


        };

    }

    private int getFluid(){

        if (manaType == ModFluidTypes.PURPLE_MANA.get()){
            return 0;
        }
        return -1;
    }

    public void onDestroy(){

        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());

        for (int i = 0; i < itemHandler.getSlots(); i++){

            inventory.setItem(i, itemHandler.getStackInSlot(i));

        }
        Containers.dropContents(this.level, this.worldPosition, inventory);

    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockstate, ManaExtractorBlockEntity blockEntity){

        if (canProgress(blockEntity)){
            blockEntity.milliBuckets++;
            Logger.getAnonymousLogger().info("Progress: " +  blockEntity.progress + " / " + blockEntity.maxProgress);
            if (blockEntity.progress++ >= blockEntity.maxProgress){
                complete(blockEntity);
            }

        }
        else {
            blockEntity.progress = 0;
        }
        setChanged(level, blockPos, blockstate);

    }

    private static boolean canProgress(ManaExtractorBlockEntity blockEntity) {
        ItemStack filledBuckets = blockEntity.itemHandler.getStackInSlot(5);

        //Logger.getAnonymousLogger().info("TEST: " + (filledBuckets.getMaxStackSize() != filledBuckets.getCount()) + " -- " + (blockEntity.milliBuckets < blockEntity.maxMilliBuckets) + " -- " +  (blockEntity.itemHandler.getStackInSlot(0).getItem()instanceof ShardOreItem SO && (blockEntity.manaType == null || blockEntity.manaType == SO.getFluid())));


        if ( (filledBuckets.getMaxStackSize() != filledBuckets.getCount()) && blockEntity.milliBuckets < blockEntity.maxMilliBuckets && blockEntity.itemHandler.getStackInSlot(0).getItem()instanceof ShardOreItem shardOreItem && (blockEntity.manaType == null || blockEntity.manaType == shardOreItem.getFluid())){

            for(int i = 2; i <= 4; i++){
                ItemStack itemStack = blockEntity.itemHandler.getStackInSlot(i);
                if(itemStack.isEmpty() || (itemStack.getItem() == shardOreItem.getShard() && itemStack.getCount() + shardOreItem.maxDrop <= itemStack.getMaxStackSize())) {
                    //Logger.getAnonymousLogger().info("true");
                    return true;
                }
            }

        }
        //Logger.getAnonymousLogger().info("false");

        return false;
    }

    private static void complete(ManaExtractorBlockEntity blockEntity) {
        blockEntity.progress = 0;
        if (blockEntity.milliBuckets >= 1000 && blockEntity.itemHandler.getStackInSlot(1).getItem() == Items.BUCKET){

            blockEntity.itemHandler.getStackInSlot(1).shrink(1);

            if(blockEntity.itemHandler.getStackInSlot(5).isEmpty()){
                blockEntity.itemHandler.setStackInSlot(5, ModItems.PURPLE_MANA_BUCKET.get().getDefaultInstance());
            }
            else{
                blockEntity.itemHandler.getStackInSlot(5).grow(1);
            }

        }
        ShardOreItem shardOreItem = (ShardOreItem) blockEntity.itemHandler.getStackInSlot(0).getItem();
        blockEntity.itemHandler.getStackInSlot(0).shrink(1);
        int x = shardOreItem.generateDrops();
            for(int i = 2; i <= 4; i++){
                ItemStack itemStack = blockEntity.itemHandler.getStackInSlot(i);
                if(itemStack.isEmpty()){
                    blockEntity.itemHandler.setStackInSlot(i, new ItemStack(shardOreItem.getShard(), x));
                    return;
                }
                int difference = itemStack.getMaxStackSize() - itemStack.getCount();
                if (difference > 0 && itemStack.getItem() == shardOreItem.getShard()) {
                   if (difference >= x ){
                       itemStack.grow(x);
                       return;
                   }
                   else{

                       itemStack.grow(difference);
                       x -= difference;
                   }

                }

            }

    }
}
