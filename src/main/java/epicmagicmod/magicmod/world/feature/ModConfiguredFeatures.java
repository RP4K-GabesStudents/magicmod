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

    public static final Supplier<List<OreConfiguration.TargetBlockState>>GRAZINOUS_ORES = Suppliers.memoize(()-> Arrays.asList(
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.GrazinousOre.get().defaultBlockState())
    ));

    public static final Supplier<List<OreConfiguration.TargetBlockState>>TORINTRIN_ORES = Suppliers.memoize(()-> Arrays.asList(
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.TorintrinOre.get().defaultBlockState())
            ));

    public static final Supplier<List<OreConfiguration.TargetBlockState>>NETHER_ORES = Suppliers.memoize(()-> Arrays.asList(
            OreConfiguration.target(OreFeatures.NETHERRACK, ModBlocks.BlaciteOre.get().defaultBlockState())
    ));

    public static final Supplier<List<OreConfiguration.TargetBlockState>>END_ORES = Suppliers.memoize(()-> Arrays.asList(
            OreConfiguration.target(new BlockMatchTest(Blocks.END_STONE), ModBlocks.MallumonOre.get().defaultBlockState())
    ));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> TROLL_GRASS = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.GRASS_BLOCK), ModBlocks.GRASS.get().defaultBlockState())
    ));

    public static final RegistryObject<ConfiguredFeature<?,?>> GRASS = CONFIGURED_FEATURE.register("grass",
            ()-> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(TROLL_GRASS.get(), 3))); // second is vein size
    //Ore may not be best but I'm lazy

    public static final RegistryObject<ConfiguredFeature<?,?>>CONFIGURED_GRAZINOUS = CONFIGURED_FEATURE.register("grazinousore",()->new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(GRAZINOUS_ORES.get(),6 /*vein size*/)));
    public static final RegistryObject<ConfiguredFeature<?,?>>CONFIGURED_TORINTRIN = CONFIGURED_FEATURE.register("torintrinore",()->new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(TORINTRIN_ORES.get(),6 /*vein size*/)));
    public static final RegistryObject<ConfiguredFeature<?,?>>CONFIGURED_BLACITE = CONFIGURED_FEATURE.register("blaciteore",()->new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(NETHER_ORES.get(),6 /*vein size*/)));
    public static final RegistryObject<ConfiguredFeature<?,?>>CONFIGURED_MALLUMON = CONFIGURED_FEATURE.register("mallumonore",()->new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(END_ORES.get(),6 /*vein size*/)));



}
