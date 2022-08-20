package epicmagicmod.magicmod.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {

    public static final CreativeModeTab CREATIVETAB_TAB = new CreativeModeTab("Sorcery") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.BLACITE.get());
        }
    };

}
