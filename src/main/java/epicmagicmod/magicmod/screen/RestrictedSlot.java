package epicmagicmod.magicmod.screen;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class RestrictedSlot extends SlotItemHandler {
    private final Item [] allowedType;


    public RestrictedSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
        allowedType = new Item[0];
    }

    public RestrictedSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Item [] itemType)
    {
        super(itemHandler, index, xPosition, yPosition);
        allowedType = itemType;
    }

    @Override
    public boolean mayPlace(ItemStack pStack) {

        for(Item i : allowedType)
        {
            if(pStack.getItem() == i) {
                return super.mayPlace(pStack);
            }
        }
        return false;
    }
}
