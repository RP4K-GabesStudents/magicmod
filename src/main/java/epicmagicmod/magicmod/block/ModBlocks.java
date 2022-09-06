package epicmagicmod.magicmod.block;

import epicmagicmod.magicmod.fluid.ModFluids;
import epicmagicmod.magicmod.items.ModCreativeModeTab;
import epicmagicmod.magicmod.items.ModItems;
import epicmagicmod.magicmod.main;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, main.MODID);

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

    private static <T extends Block>RegistryObject<T> registerBlock(String oreName, Supplier<T> block, CreativeModeTab tab){

        RegistryObject<T> XBlock = BLOCKS.register(oreName, block);
        registerBlockItem(oreName, XBlock, tab);
        return XBlock;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String oreName, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(oreName, ()->new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    //Register as Blocks.Register and then create an item ore variant
    //------------------------------------------------------------------ORES
    public static final RegistryObject<Block>GrazinousOre = BLOCKS.register("grazinousore", ()->new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(5).requiresCorrectToolForDrops(), UniformInt.of(10, 20)));
    public static final RegistryObject<ShardOreItem>GrazinousOreItem = ModItems.ITEMS.register("grazinousore", ()-> new ShardOreItem(GrazinousOre.get(), new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB), 4, 2, 48, ShardOreItem.EOreType.Granizous));
    public static final RegistryObject<Block>TorintrinOre = BLOCKS.register("torintrinore", ()->new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3).requiresCorrectToolForDrops(), UniformInt.of(10, 20)));
    public static final RegistryObject<ShardOreItem>TorintrinOreItem = ModItems.ITEMS.register("torintrinore", ()-> new ShardOreItem(TorintrinOre.get(), new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB), 4, 3, 36, ShardOreItem.EOreType.Torintrin));
    public static final RegistryObject<Block>BlaciteOre = BLOCKS.register("blaciteore", ()->new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(6).requiresCorrectToolForDrops(), UniformInt.of(20, 40)));
    public static final RegistryObject<ShardOreItem>BlaciteOreItem = ModItems.ITEMS.register("blaciteore", ()-> new ShardOreItem(BlaciteOre.get(), new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB), 3, 2, 84, ShardOreItem.EOreType.Blacite));
    public static final RegistryObject<Block>MallumonOre = BLOCKS.register("mallumonore", ()->new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(8).requiresCorrectToolForDrops(), UniformInt.of(50, 100)));
    public static final RegistryObject<ShardOreItem>MallumonOreItem = ModItems.ITEMS.register("mallumonore", ()-> new ShardOreItem(MallumonOre.get(), new Item.Properties().tab(ModCreativeModeTab.CREATIVETAB_TAB), 3, 1, 120, ShardOreItem.EOreType.Mallumon));

    public static final RegistryObject<Block> GRASS = BLOCKS.register("grass", () -> new EpicDirt(BlockBehaviour.Properties.of(Material.GRASS).strength(0.6F).sound(SoundType.GRASS)));


    //---------------------------------------------------------------------------------------------------------------------------------------------------------

    //-------------------------------------------------- BLOCKS  --------------------------------------------------
    public static final RegistryObject<ManaLiquidBlock> GRAZINOUS_SOURCE_BLOCK = BLOCKS.register("grazinous_source_block",
            ()-> new ManaLiquidBlock(ModFluids.SOURCE_GRAZINOUS_MANA, BlockBehaviour.Properties.copy(Blocks.WATER), 10, new MobEffectInstance[]
                    {
                            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 4000, 1),
                            new MobEffectInstance(MobEffects.WATER_BREATHING, 4000, 0),
                            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 4000, 1)
                    }));

    public static final RegistryObject<ManaLiquidBlock> TORINTRIN_SOURCE_BLOCK = BLOCKS.register("torintrin_source_block",
            ()-> new ManaLiquidBlock(ModFluids.SOURCE_TORINTRIN_MANA, BlockBehaviour.Properties.copy(Blocks.LAVA), 6, new MobEffectInstance[]
                    {
                            new MobEffectInstance(MobEffects.HUNGER, 500, 0),
                            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2000, 1),
                            new MobEffectInstance(MobEffects.DIG_SPEED, 2000, 1),
                            new MobEffectInstance(MobEffects.GLOWING, 2000, 0)
                    }));

    public static final RegistryObject<ManaLiquidBlock> BLACITE_SOURCE_BLOCK = BLOCKS.register("blacite_source_block",
            ()-> new ManaLiquidBlock(ModFluids.SOURCE_BLACITE_MANA, BlockBehaviour.Properties.copy(Blocks.LAVA), 15, new MobEffectInstance[]
                    {
                            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 5000, 2),
                            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5000, 2),
                            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5000, 2)
                    }));

    public static final RegistryObject<ManaLiquidBlock> MALLUMON_SOURCE_BLOCK = BLOCKS.register("mallumon_source_block",
            ()-> new ManaLiquidBlock(ModFluids.SOURCE_MALLUMON_MANA, BlockBehaviour.Properties.copy(Blocks.WATER), 50, new MobEffectInstance[]
                    {
                            new MobEffectInstance(MobEffects.BAD_OMEN, 3000, 1),
                            new MobEffectInstance(MobEffects.UNLUCK, 3000, 1),
                            new MobEffectInstance(MobEffects.BLINDNESS, 3000, 1),
                            new MobEffectInstance(MobEffects.CONFUSION, 800, 1),
                            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3000, 4),
                            new MobEffectInstance(MobEffects.INVISIBILITY, 3000, 1)
                    }));

    //Change texture


    public static final RegistryObject<Block> MANA_EXTRACTOR = registerBlock("manaextractor",
            ()-> new ManaExtractorBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3).requiresCorrectToolForDrops()), ModCreativeModeTab.CREATIVETAB_TAB);

    public static final RegistryObject<Block> WAND_ALTAR = registerBlock("wandaltar",
            ()-> new WandEnhanceBlock(BlockBehaviour.Properties.of(Material.SCULK).strength(9999).requiresCorrectToolForDrops()), ModCreativeModeTab.CREATIVETAB_TAB);

}
