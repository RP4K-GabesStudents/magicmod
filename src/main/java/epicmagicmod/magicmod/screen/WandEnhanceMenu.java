package epicmagicmod.magicmod.screen;

import epicmagicmod.magicmod.block.ModBlocks;
import epicmagicmod.magicmod.block.ShardOreItem;
import epicmagicmod.magicmod.block.entity.ManaExtractorBlockEntity;
import epicmagicmod.magicmod.items.ModItems;
import epicmagicmod.magicmod.items.wands.WandParent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;


public class WandEnhanceMenu extends AbstractContainerMenu {



    public final ManaExtractorBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;





    protected WandEnhanceMenu(int pContainerId, Inventory inv, FriendlyByteBuf data) {
        this(pContainerId, inv, inv.player.level.getBlockEntity(data.readBlockPos()), new SimpleContainerData(6));
    }

    public WandEnhanceMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data){
        super(ModMenuType.MANA_MENU.get(), pContainerId);
        checkContainerSize(inv, 6);
        blockEntity = (ManaExtractorBlockEntity) entity;
        level = inv.player.level;
        this.data = data;
        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new RestrictedSlot(iItemHandler, 0, 79, 40, new Item[0], WandParent.class)); // WAND
            addSlot(new RestrictedSlot(iItemHandler, 1, 46, 12, new Item[] {ModItems.WANDCORE.get()}, null));
            addSlot(new RestrictedSlot(iItemHandler, 2, 79, 6, new Item[] {ModItems.WANDCORE.get()}, null));
            addSlot(new RestrictedSlot(iItemHandler, 3, 112, 12, new Item[] {ModItems.WANDCORE.get()}, null));
            addSlot(new RestrictedSlot(iItemHandler, 4, 26, 40, new Item[] {ModItems.GRAZINOUS_SHARD.get(), ModItems.TORINTRIN_SHARD.get(), ModItems.BLACITE_SHARD.get(), ModItems.MALLUMON_SHARD.get()}, null));
            addSlot(new RestrictedSlot(iItemHandler, 5, 46, 68, new Item[] {ModItems.GRAZINOUS_MANA_BUCKET.get(), ModItems.TORINTRIN_MANA_BUCKET.get(), ModItems.BLACITE_MANA_BUCKET.get(), ModItems.MALLUMON_MANA_BUCKET.get()}, null));
            addSlot(new RestrictedSlot(iItemHandler, 4, 79, 74, new Item[] {ModItems.GRAZINOUS_SHARD.get(), ModItems.TORINTRIN_SHARD.get(), ModItems.BLACITE_SHARD.get(), ModItems.MALLUMON_SHARD.get()}, null));
            addSlot(new RestrictedSlot(iItemHandler, 5, 112, 168, new Item[] {ModItems.GRAZINOUS_MANA_BUCKET.get(), ModItems.TORINTRIN_MANA_BUCKET.get(), ModItems.BLACITE_MANA_BUCKET.get(), ModItems.MALLUMON_MANA_BUCKET.get()}, null));
            addSlot(new RestrictedSlot(iItemHandler, 4, 132, 40, new Item[] {ModItems.GRAZINOUS_SHARD.get(), ModItems.TORINTRIN_SHARD.get(), ModItems.BLACITE_SHARD.get(), ModItems.MALLUMON_SHARD.get()}, null));
        });
        addDataSlots(data);
    }



    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    //its a sandwich :D

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 9;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        }
        else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }



    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, ModBlocks.MANA_EXTRACTOR.get());
    }

    public boolean isCrafting()
    {
        return data.get(0) > 0; // If progress is > 0
    }

    public int getProgress()
    {
        int progress = data.get(0);
        int max = data.get(1);
        int arrowSize = 18;
        return  max != 0 && progress != 0 ? progress * arrowSize / max : 0;
    }

    public boolean getValidItem(int idx)
    {
        int progress = data.get(2);
        int max = data.get(3);
        int arrowSize = 64;
        return true;
        //return  max != 0 && progress != 0 ? progress * arrowSize / max : 0;
    }

    public int getFluidColor()
    {
        return data.get(4);
    }

}
