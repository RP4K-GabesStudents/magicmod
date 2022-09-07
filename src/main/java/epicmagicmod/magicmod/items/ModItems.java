package epicmagicmod.magicmod.items;


import epicmagicmod.magicmod.block.ModBlocks;
import epicmagicmod.magicmod.block.ShardOreItem;
import epicmagicmod.magicmod.fluid.ModFluids;
import epicmagicmod.magicmod.items.armor.ModArmorMaterials;
import epicmagicmod.magicmod.items.utilities.TimeDial;
import epicmagicmod.magicmod.items.utilities.ManaBucket;
import epicmagicmod.magicmod.items.wands.*;
import epicmagicmod.magicmod.main;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableArmorItem;
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
    public static final RegistryObject<Item>FIREWAND = ITEMS.register("firewand", ()->new FireWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).fireResistant(), 10000, 2500, "Fire Wand", 1.25f, ShardOreItem.EOreType.Blacite));// Shoots a 2D circle of fire balls, (8 total) also gives user 50 ticks of fire resistance and resistance. Also increases force of wand

    //COMPLETED
    public static final RegistryObject<Item>ICEWAND = ITEMS.register("icewand", ()->new IceWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).fireResistant(), 1000, 25000,  "Ice Wand", 0.75f, ShardOreItem.EOreType.Granizous, 60, 300));
    // increaes range and size, Alt attack now gives mining fatigue
    // increases range and size, both also now give blindness to affected entities

    //COMPLETED
    public static final RegistryObject<Item>LIGHTNINGWAND = ITEMS.register("lightningwand", ()->new LightningWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).fireResistant(), 10000, 10000, "Lightning Wand", 1.5f, ShardOreItem.EOreType.Torintrin, 1));
    // Increases AOE
    // Increases AOE, Lightning now strikes the same place 3 times

    //COMPLETED
    public static final RegistryObject<Item>TELEPORTWAND = ITEMS.register("teleportwand", ()->new TeleportWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).fireResistant(), 20000, 50000, "Teleport Wand", 0.6f, ShardOreItem.EOreType.Torintrin));
    // gives speed boost and jump boost on teleport (for both entities) -- Removes Naseua effect
    // Also allows TPing of Bosses -- Also gives swapped entites nasuea

    //COMPLETED
    public static final RegistryObject<Item>GAPPLEWAND = ITEMS.register("gapplewand", ()->new GappleWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).fireResistant(), 10000, 2500, "GApple Wand",0.9f, ShardOreItem.EOreType.Granizous)); // Fully heals health bar
    // Full heals hunger bar
    //Gives Resistance, Absorption and Regeneration

    //COMPLETED
    public static final RegistryObject<Item>THRUSTWAND = ITEMS.register("thrustwand", ()->new ThrustWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).fireResistant(), 2500, 1000, "Thrust Wand",1.15f, ShardOreItem.EOreType.Mallumon, 1, 1)); // Throws user up or down, [when thrown down, gives the user increased jump height -- to prevent fall death]
    // Wand no longer affects user
    // Increased thrust force

    //COMPLETED
    public static final RegistryObject<Item>BINDWAND = ITEMS.register("bindwand", ()->new BindWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).fireResistant(), 25000, 25000, "Bind Wand",2f, ShardOreItem.EOreType.Mallumon)); // Bind Two Targets
    // Wand no longer binds to the player as well as the two targeted entities
    // Can target bosses

    public static final RegistryObject<Item>RANDOMIZERWAND = ITEMS.register("randomizerwand", ()->new RandomizerWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).fireResistant(), 25000, 25000, "Randomizer Wand",2f, ShardOreItem.EOreType.Granizous)); // Bind Two Targets

    public static final RegistryObject<Item>LIFESTEALWAND = ITEMS.register("lifestealwand", ()->new LifeStealWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).fireResistant(), 25000, 25000, "Lifesteal Wand",2f, ShardOreItem.EOreType.Mallumon)); // Bind Two Targets

    public static final RegistryObject<Item>SUMMONINGWAND = ITEMS.register("summoningwand", ()->new SummoningWand(new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1).fireResistant(), 25000, 25000, "Summoning Wand",2f, ShardOreItem.EOreType.Mallumon)); // Bind Two Targets



    //SYLVRS REQUEST -- Clock that changes night to day
    public static final RegistryObject<Item>TIMEDIAL = ITEMS.register("timedial", ()->new TimeDial(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.CREATIVETAB_TAB)));

    //SYLVRS REQUEST -- Clock that changes night to day
    public static final RegistryObject<Item>WANDCORE = ITEMS.register("wandcore", ()->new Item(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.CREATIVETAB_TAB)));


    //-----------------------------------------------------------------ARMORS-----------------------------------------------------------------
    public static final RegistryObject<Item>ADEPT_BOOTS = ITEMS.register("adeptboots", ()->new DyeableArmorItem(ModArmorMaterials.ADEPT, EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>ADEPT_LEGS = ITEMS.register("adeptleggings", ()->new DyeableArmorItem(ModArmorMaterials.ADEPT, EquipmentSlot.LEGS, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>ADEPT_CHEST = ITEMS.register("adeptchestplate", ()->new DyeableArmorItem(ModArmorMaterials.ADEPT, EquipmentSlot.CHEST, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>ADEPT_HELMET = ITEMS.register("adepthelmet", ()->new DyeableArmorItem(ModArmorMaterials.ADEPT, EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));

    public static final RegistryObject<Item>EMPOWERED_BOOTS = ITEMS.register("empoweredboots", ()->new ArmorItem(ModArmorMaterials.EMPOWERED, EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>EMPOWERED_LEGS = ITEMS.register("empoweredleggings", ()->new ArmorItem(ModArmorMaterials.EMPOWERED, EquipmentSlot.LEGS, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>EMPOWERED_CHEST = ITEMS.register("empoweredchestplate", ()->new ArmorItem(ModArmorMaterials.EMPOWERED, EquipmentSlot.CHEST, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));
    public static final RegistryObject<Item>EMPOWERED_HELMET = ITEMS.register("empoweredhelmet", ()->new ArmorItem(ModArmorMaterials.EMPOWERED, EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB).stacksTo(1)));


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
