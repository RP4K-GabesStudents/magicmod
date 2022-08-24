package epicmagicmod.magicmod.world.structure;

import epicmagicmod.magicmod.main;
import epicmagicmod.magicmod.world.structure.structures.ExampleStructure;
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

    public static final RegistryObject<StructureType<ExampleStructure>>EXAMPLE_STRUCTURE = STRUCTURES.register("example_structure", ()-> ()-> ExampleStructure.CODEC);





}