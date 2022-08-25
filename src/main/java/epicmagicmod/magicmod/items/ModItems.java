package epicmagicmod.magicmod.items;

import epicmagicmod.magicmod.block.ModBlocks;
import epicmagicmod.magicmod.block.ShardOreItem;
import epicmagicmod.magicmod.fluid.ModFluidTypes;
import epicmagicmod.magicmod.fluid.ModFluids;
import epicmagicmod.magicmod.items.armor.ModArmorMaterials;
import epicmagicmod.magicmod.items.utilities.ManaBucket;
import epicmagicmod.magicmod.items.wands.*;
import epicmagicmod.magicmod.main;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item>ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, main.MODID);

    // always keep with the DeferredRegister
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);

    }

    public static final RegistryObject<Item>BLACITE = ITEMS.register("blacite", ()->new Item(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB)));
    public static final RegistryObject<Item>FIREWAND = ITEMS.register("firewand", ()->new FireWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>ICEWAND = ITEMS.register("icewand", ()->new IceWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>LIGHTNINGWAND = ITEMS.register("lightningwand", ()->new LightningWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>TELEPORTWAND = ITEMS.register("teleportwand", ()->new TeleportWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>GAPPLEWAND = ITEMS.register("gapplewand", ()->new GappleWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>PURPLE_WIZARD_ROBE_BOOTS = ITEMS.register("purplewizardboots", ()->new ArmorItem(ModArmorMaterials.ROBES, EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>PURPLE_WIZARD_ROBE_LEGS = ITEMS.register("purplewizardlegs", ()->new ArmorItem(ModArmorMaterials.ROBES, EquipmentSlot.LEGS, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>PURPLE_WIZARD_ROBE_CHEST = ITEMS.register("purplewizardchest", ()->new ArmorItem(ModArmorMaterials.ROBES, EquipmentSlot.CHEST, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>PURPLE_WIZARD_ROBE_HELMET = ITEMS.register("purplewizardhelmet", ()->new ArmorItem(ModArmorMaterials.ROBES, EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<ManaBucket>PURPLE_MANA_BUCKET = ITEMS.register("mana_gain_fluid", ()->new ManaBucket(()-> ModFluids.SOURCE_PURPLE_MANA.get(), new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(16).fireResistant(), 32));

}
