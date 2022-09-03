package epicmagicmod.magicmod.screen;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class RestrictedSlot extends SlotItemHandler {
    private final Item [] allowedType;

    private final Object type;


    public RestrictedSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
        allowedType = new Item[0];
        type = null;
    }

    public RestrictedSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, @Nullable Item [] itemType, @Nullable Object type)
    {
        super(itemHandler, index, xPosition, yPosition);
        allowedType = itemType;
        this.type = type;
    }

    @Override
    public boolean mayPlace(ItemStack pStack) {

        if(type != null && pStack.getItem().getClass() == type)
        {
            return true;
        }

        for(Item i : allowedType)
        {
            if(pStack.getItem() == i) {
                return super.mayPlace(pStack);
            }
        }
        return false;
    }
}
