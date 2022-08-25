package epicmagicmod.magicmod;

import com.mojang.logging.LogUtils;
import epicmagicmod.magicmod.block.ModBlocks;
import epicmagicmod.magicmod.block.ShardOreItem;
import epicmagicmod.magicmod.block.entity.ModBlockEntities;
import epicmagicmod.magicmod.effect.ModEffects;
import epicmagicmod.magicmod.fluid.ModFluidTypes;
import epicmagicmod.magicmod.fluid.ModFluids;
import epicmagicmod.magicmod.items.ModCreativeModeTab;
import epicmagicmod.magicmod.items.ModItems;
import epicmagicmod.magicmod.networking.ModMessages;
import epicmagicmod.magicmod.screen.ManaExtractionScreen;
import epicmagicmod.magicmod.screen.ModMenuType;
import epicmagicmod.magicmod.world.feature.ModConfiguredFeatures;
import epicmagicmod.magicmod.world.feature.ModPlacedFeatures;
import epicmagicmod.magicmod.world.structure.ModStructures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(main.MODID)
public class main {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "magicmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "magicmod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "magicmod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Creates a new Block with the id "magicmod:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));
    // Creates a new BlockItem with the id "magicmod:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);

        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        ModItems.register(modEventBus);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);
        ModStructures.register(modEventBus);

        ModEffects.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuType.register(modEventBus);
        ModBlocks.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
        ModMessages.register();
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_PURPLE_MANA.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_PURPLE_MANA.get(), RenderType.translucent());

            MenuScreens.register(ModMenuType.MANA_MENU.get(), ManaExtractionScreen::new);

            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }



}
