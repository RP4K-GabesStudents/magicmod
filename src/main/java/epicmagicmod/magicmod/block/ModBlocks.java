package epicmagicmod.magicmod.block;

import epicmagicmod.magicmod.fluid.ModFluids;
import epicmagicmod.magicmod.items.ModCreativeModeTab;
import epicmagicmod.magicmod.items.ModItems;
import epicmagicmod.magicmod.main;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.LiquidBlock;
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

    public static final RegistryObject<Block>BlaciteOre = registerBlock("blaciteore", ()->new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3).requiresCorrectToolForDrops(), UniformInt.of(10, 20)), ModCreativeModeTab.CREATIVETAB_TAB);


    public static final RegistryObject<LiquidBlock> PURPLE_SOURCE_BLOCK = BLOCKS.register("mana_source_block",
            ()-> new ManaLiquidBlock(ModFluids.SOURCE_PURPLE_MANA, BlockBehaviour.Properties.copy(Blocks.WATER)));

}
