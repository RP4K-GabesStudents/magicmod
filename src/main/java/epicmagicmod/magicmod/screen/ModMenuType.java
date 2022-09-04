package epicmagicmod.magicmod.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import epicmagicmod.magicmod.main;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class ModMenuType {

    public static final DeferredRegister<MenuType<?>>MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, main.MODID);

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);

    }
    public static final RegistryObject<MenuType<ManaExtractionMenu>>MANA_MENU = registerMenuType(ManaExtractionMenu::new, "mana_extraction_menu");
    public static final RegistryObject<MenuType<WandEnhanceMenu>>WAND_MENU = registerMenuType(WandEnhanceMenu::new, "wand_altar");
    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>>registerMenuType(IContainerFactory<T> factory, String name){

        return MENUS.register(name, ()-> IForgeMenuType.create(factory));
    }
}
