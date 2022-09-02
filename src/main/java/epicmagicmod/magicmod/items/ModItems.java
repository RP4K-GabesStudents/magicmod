package epicmagicmod.magicmod.items;


import epicmagicmod.magicmod.block.ManaLiquidBlock;
import epicmagicmod.magicmod.block.ModBlocks;
import epicmagicmod.magicmod.fluid.ModFluids;
import epicmagicmod.magicmod.items.armor.ModArmorMaterials;
import epicmagicmod.magicmod.items.utilities.TimeDial;
import epicmagicmod.magicmod.items.utilities.ManaBucket;
import epicmagicmod.magicmod.items.wands.*;
import epicmagicmod.magicmod.main;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
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
    //-----------------------------------------------------------------WANDS-----------------------------------------------------------------

    //COMPLETED
    public static final RegistryObject<Item>FIREWANDA = ITEMS.register("firewand", ()->new FireWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.UNCOMMON), 10000, 2500, 1));
    public static final RegistryObject<Item>FIREWANDB = ITEMS.register("firewand1", ()->new FireWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.RARE), 50000, 1000, 2)); // Shoots a 2D circle of fire balls, (8 total) also gives user 50 ticks of fire resistance and resistance. Also increases force of wand
    public static final RegistryObject<Item>FIREWANDC = ITEMS.register("firewand2", ()->new FireWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.EPIC), 100000, 0, 3));

    //COMPLETED
    public static final RegistryObject<Item>ICEWANDA = ITEMS.register("icewand", ()->new IceWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.UNCOMMON), 1000, 25000, 1, 60, 300));
    public static final RegistryObject<Item>ICEWANDB = ITEMS.register("icewand1", ()->new IceWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.RARE), 2000, 35000, 2, 90, 450 )); // increaes range and size, Alt attack now gives mining fatigue
    public static final RegistryObject<Item>ICEWANDC = ITEMS.register("icewand2", ()->new IceWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.EPIC), 5000, 45000, 3, 120, 600)); // increases range and size, both also now give blindness to affected entities

    //COMPLETED
    public static final RegistryObject<Item>LIGHTNINGWANDA = ITEMS.register("lightningwand", ()->new LightningWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.UNCOMMON), 10000, 10000, 1, 1,0));
    public static final RegistryObject<Item>LIGHTNINGWANDB = ITEMS.register("lightningwand1", ()->new LightningWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.RARE), 20000, 20000, 2, 2, 1)); // Increases AOE
    public static final RegistryObject<Item>LIGHTNINGWANDC = ITEMS.register("lightningwand2", ()->new LightningWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.EPIC), 50000, 50000, 3, 3, 2)); // Increases AOE, Lightning now strikes the same place 3 times

    //COMPLETED
    public static final RegistryObject<Item>TELEPORTWANDA = ITEMS.register("teleportwand", ()->new TeleportWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.UNCOMMON), 20000, 50000, 1));
    public static final RegistryObject<Item>TELEPORTWANDB = ITEMS.register("teleportwand1", ()->new TeleportWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.RARE), 10000, 40000, 2)); // gives speed boost and jump boost on teleport (for both entities) -- Removes Naseua effect
    public static final RegistryObject<Item>TELEPORTWANDC = ITEMS.register("teleportwand2", ()->new TeleportWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.EPIC), 1000, 25000, 3)); // Also allows TPing of Bosses -- Also gives swapped entites nasuea

    //COMPLETED
    public static final RegistryObject<Item>GAPPLEWANDA = ITEMS.register("gapplewand", ()->new GappleWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.UNCOMMON), 10000, 2500, 1)); // Fully heals health bar
    public static final RegistryObject<Item>GAPPLEWANDB = ITEMS.register("gapplewand1", ()->new GappleWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.RARE), 10000, 2500, 2)); // Full heals hunger bar
    public static final RegistryObject<Item>GAPPLEWANDC = ITEMS.register("gapplewand2", ()->new GappleWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.EPIC), 10000, 2500, 3)); //Gives Resistance, Absorption and Regeneration

    //COMPLETED
    public static final RegistryObject<Item>THRUSTWANDA = ITEMS.register("thrustwand", ()->new ThrustWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.UNCOMMON), 2500, 1000, 1,1, 1)); // Throws user up or down, [when thrown down, gives the user increased jump height -- to prevent fall death]
    public static final RegistryObject<Item>THRUSTWANDB = ITEMS.register("thrustwand1", ()->new ThrustWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.RARE), 10000, 10000, 2, 2,2)); // Wand no longer affects user
    public static final RegistryObject<Item>THRUSTWANDC = ITEMS.register("thrustwand2", ()->new ThrustWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.EPIC), 10000, 10000, 3,4, 3)); // Increased thrust force

    //COMPLETED
    public static final RegistryObject<Item>BINDWANDA = ITEMS.register("bindwand", ()->new BindWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.UNCOMMON), 25000, 25000, 1)); // Bind Two Targets
    public static final RegistryObject<Item>BINDWANDB = ITEMS.register("bindwand1", ()->new BindWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.RARE), 50000, 50000, 2)); // Wand no longer binds to the player as well as the two targeted entities
    public static final RegistryObject<Item>BINDWANDC = ITEMS.register("bindwand2", ()->new BindWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).rarity(Rarity.EPIC), 100000, 100000, 3)); // Can target bosses

    //SYLVRS REQUEST -- Clock that changes night to day
    public static final RegistryObject<Item>TIMEDIAL = ITEMS.register("timedial", ()->new TimeDial(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB)));

    //-----------------------------------------------------------------ARMORS-----------------------------------------------------------------
    public static final RegistryObject<Item>PURPLE_WIZARD_ROBE_BOOTS = ITEMS.register("purplewizardboots", ()->new ArmorItem(ModArmorMaterials.ROBES, EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>PURPLE_WIZARD_ROBE_LEGS = ITEMS.register("purplewizardlegs", ()->new ArmorItem(ModArmorMaterials.ROBES, EquipmentSlot.LEGS, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>PURPLE_WIZARD_ROBE_CHEST = ITEMS.register("purplewizardchest", ()->new ArmorItem(ModArmorMaterials.ROBES, EquipmentSlot.CHEST, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>PURPLE_WIZARD_ROBE_HELMET = ITEMS.register("purplewizardhelmet", ()->new ArmorItem(ModArmorMaterials.ROBES, EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));


    //-----------------------------------------------------------------SHARDS & BUCKETS-----------------------------------------------------------------
    public static final RegistryObject<Item> GRAZINOUS_SHARD = ITEMS.register("grazinous", ()->new Item(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<ManaBucket>GRAZINOUS_MANA_BUCKET = ITEMS.register("grazinousbucket", ()->new ManaBucket(()-> ModFluids.SOURCE_GRAZINOUS_MANA.get(), new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(16).fireResistant().rarity(Rarity.UNCOMMON), 36,ModBlocks.GRAZINOUS_SOURCE_BLOCK.get()));
    public static final RegistryObject<Item>TORINTRIN_SHARD = ITEMS.register("torintrin", ()->new Item(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<ManaBucket>TORINTRIN_MANA_BUCKET = ITEMS.register("torintrinbucket", ()->new ManaBucket(()-> ModFluids.SOURCE_TORINTRIN_MANA.get(), new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(16).fireResistant().rarity(Rarity.UNCOMMON), 12,ModBlocks.TORINTRIN_SOURCE_BLOCK.get()));
    public static final RegistryObject<Item> BLACITE_SHARD = ITEMS.register("blacite", ()->new Item(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<ManaBucket>BLACITE_MANA_BUCKET = ITEMS.register("blacitebucket", ()->new ManaBucket(()-> ModFluids.SOURCE_BLACITE_MANA.get(), new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(16).fireResistant().rarity(Rarity.RARE), 96, ModBlocks.BLACITE_SOURCE_BLOCK.get()));
    public static final RegistryObject<Item> MALLUMON_SHARD = ITEMS.register("mallumon", ()->new Item(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).fireResistant().rarity(Rarity.EPIC)));
    public static final RegistryObject<ManaBucket>MALLUMON_MANA_BUCKET = ITEMS.register("mallumonbucket", ()->new ManaBucket(()-> ModFluids.SOURCE_MALLUMON_MANA.get(), new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(16).fireResistant().rarity(Rarity.EPIC), 60,ModBlocks.MALLUMON_SOURCE_BLOCK.get()));



}
