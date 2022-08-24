package epicmagicmod.magicmod.block.entity;

import epicmagicmod.magicmod.block.ModBlocks;
import epicmagicmod.magicmod.main;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, main.MODID);

    // always keep with the DeferredRegister
    public static void register(IEventBus eventBus){
        BLOCK_ENTITY.register(eventBus);

    }

    public static final RegistryObject<BlockEntityType<ManaExtractorBlockEntity>>MANA_EXTRACTOR =
            BLOCK_ENTITY.register("mana_extractor",
                    ()-> BlockEntityType.Builder.of(ManaExtractorBlockEntity::new, ModBlocks.MANA_EXTRACTOR.get()).build(null));




}
