package epicmagicmod.magicmod.world.structure;

import epicmagicmod.magicmod.main;
import epicmagicmod.magicmod.world.structure.structures.*;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {

    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, main.MODID);

    // always keep with the DeferredRegister
    public static void register(IEventBus eventBus){
        STRUCTURES.register(eventBus);

    }

    public static final RegistryObject<StructureType<Tent>>TENT = STRUCTURES.register("tent_structure", ()-> ()-> Tent.CODEC);

    public static final RegistryObject<StructureType<DomTomb>>DOM_TOMB = STRUCTURES.register("dom_tomb_structure", ()-> ()-> DomTomb.CODEC);

    public static final RegistryObject<StructureType<UndergroundStation>>UNDERGROUND_STATION = STRUCTURES.register("underground_station_structure", ()-> ()-> UndergroundStation.CODEC);

    public static final RegistryObject<StructureType<SurfaceStation>>SURFACE_STATION = STRUCTURES.register("surface_station_structure", ()-> ()-> SurfaceStation.CODEC);

    public static final RegistryObject<StructureType<ManaTemple>>MANA_TEMPLE = STRUCTURES.register("mana_temple_structure", ()-> ()-> ManaTemple.CODEC);


    public static final RegistryObject<StructureType<Moyai>>MOYAI = STRUCTURES.register("moyai_structure", ()-> ()-> Moyai.CODEC);
    public static final RegistryObject<StructureType<Rusty>>RUSTY = STRUCTURES.register("rusty_structure", ()-> ()-> Rusty.CODEC);
    public static final RegistryObject<StructureType<Sylvr>>SYLVER = STRUCTURES.register("sylvr_structure", ()-> ()-> Sylvr.CODEC);

    public static final RegistryObject<StructureType<ArcherTower>>ARCHER_TOWER = STRUCTURES.register("archer_tower_structure", ()-> ()-> ArcherTower.CODEC);
}
