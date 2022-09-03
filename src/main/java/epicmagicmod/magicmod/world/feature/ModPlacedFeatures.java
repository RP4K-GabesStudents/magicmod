package epicmagicmod.magicmod.world.feature;

import epicmagicmod.magicmod.main;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURE = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, main.MODID);

    public static void register(IEventBus eventBus){
        PLACED_FEATURE.register(eventBus);
    }

    public static List<PlacementModifier>orePlacement(PlacementModifier A, PlacementModifier B){

        return Arrays.asList(A, InSquarePlacement.spread(), B, BiomeFilter.biome());

    }

    public static List<PlacementModifier>commonOrePlacement(int A, PlacementModifier B){

        return orePlacement(CountPlacement.of(A), B);

    }

    public static List<PlacementModifier>rareOrePlacement(int A, PlacementModifier B){

        return orePlacement(RarityFilter.onAverageOnceEvery(A), B);

    }

    public static final RegistryObject<PlacedFeature>GRAZINOUS_ORE_PLACED = PLACED_FEATURE.register("grazinous_ore_placed", ()->new PlacedFeature(
            ModConfiguredFeatures.CONFIGURED_GRAZINOUS.getHolder().get(),
            commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-200), VerticalAnchor.absolute(0)))
    ));

    public static final RegistryObject<PlacedFeature>TORINTRIN_ORE_PLACED = PLACED_FEATURE.register("torintrin_ore_placed", ()->new PlacedFeature(

            ModConfiguredFeatures.CONFIGURED_TORINTRIN.getHolder().get(),
            commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-200), VerticalAnchor.absolute(0)))

    ));

    public static final RegistryObject<PlacedFeature>BLACITE_ORE_PLACED = PLACED_FEATURE.register("blacite_ore_placed", ()->new PlacedFeature(

            ModConfiguredFeatures.CONFIGURED_BLACITE.getHolder().get(),
            commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(60), VerticalAnchor.absolute(120)))

    ));

    public static final RegistryObject<PlacedFeature>MALLUMON_ORE_PLACED = PLACED_FEATURE.register("mallumon_ore_placed", ()->new PlacedFeature(

            ModConfiguredFeatures.CONFIGURED_MALLUMON.getHolder().get(),
            commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80)))

    ));

    public static final RegistryObject<PlacedFeature> GRASS_PLACED = PLACED_FEATURE.register("grass_placed",
            ()-> new PlacedFeature(ModConfiguredFeatures.GRASS.getHolder().get(),
                    commonOrePlacement(32, //Veins per chunk
                            HeightRangePlacement.uniform(VerticalAnchor.absolute(40), VerticalAnchor.absolute(150))
                    )));


}
