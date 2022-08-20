package epicmagicmod.magicmod.world.feature;

import com.google.common.base.Suppliers;
import epicmagicmod.magicmod.block.ModBlocks;
import epicmagicmod.magicmod.main;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;


public class ModConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURE = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, main.MODID);

    public static void register(IEventBus eventBus){
        CONFIGURED_FEATURE.register(eventBus);
    }

    public static final Supplier<List<OreConfiguration.TargetBlockState>>OVERWORLD_ORES = Suppliers.memoize(()-> Arrays.asList(

            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.BlaciteOre.get().defaultBlockState()),
            OreConfiguration.target(new BlockMatchTest(Blocks.COPPER_ORE), ModBlocks.BlaciteOre.get().defaultBlockState())

    ));

    public static final RegistryObject<ConfiguredFeature<?,?>>CONFIGURED_BLACITE = CONFIGURED_FEATURE.register("blaciteore",()->new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_ORES.get(),21 /*vein size*/)));



}
