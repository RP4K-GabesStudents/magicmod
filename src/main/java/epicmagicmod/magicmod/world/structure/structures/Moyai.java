package epicmagicmod.magicmod.world.structure.structures;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import epicmagicmod.magicmod.world.structure.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Optional;

public class Moyai extends Structure {

    private final Holder<StructureTemplatePool> STARTPOOL;
    private final Optional<ResourceLocation>JIGSAW_ORIGIN;
    private final int SIZE;
    private final HeightProvider START_HEIGHT;
    private final Optional<Heightmap.Types>PROJECT_START_HEIGHT_MAP;
    private final int MAX_DISTANCE_FROM_CENTER;


    protected Moyai(StructureSettings config, Holder<StructureTemplatePool> startpool, Optional<ResourceLocation> jigsaw_origin, int size, HeightProvider start_height, Optional<Heightmap.Types> project_start_height_map, int max_distance_from_center) {
        super(config);
        STARTPOOL = startpool;
        JIGSAW_ORIGIN = jigsaw_origin;
        SIZE = size;
        START_HEIGHT = start_height;
        PROJECT_START_HEIGHT_MAP = project_start_height_map;
        MAX_DISTANCE_FROM_CENTER = max_distance_from_center;
    }

    public static final Codec<Moyai>CODEC = RecordCodecBuilder.<Moyai>mapCodec(objectInstance ->
            objectInstance.group(Moyai.settingsCodec(objectInstance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.STARTPOOL),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.JIGSAW_ORIGIN),
                    Codec.intRange(1, 2).fieldOf("size").forGetter(structure -> structure.SIZE),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.START_HEIGHT),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.PROJECT_START_HEIGHT_MAP),
                    Codec.intRange(1, 2).fieldOf("max_distance_from_center").forGetter(structure -> structure.MAX_DISTANCE_FROM_CENTER)
                    ).apply(objectInstance, Moyai::new)
            ).codec();

    private static boolean extraSpawnChecks(GenerationContext context){

        ChunkPos chunkpos = context.chunkPos();

        //NOTE return type is boolean
        /* Code for making structures only able to spawn above 120 blocks. {True if above 120}
        return context.chunkGenerator().getFirstOccupiedHeight(
                chunkpos.getMinBlockX(),
                chunkpos.getMinBlockZ(),
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                context.heightAccessor(),
                context.randomState()) > 120
        */


        //Code for spawning near a structure set.
        return context.chunkGenerator().hasStructureChunkInRange(
                StructureSets.VILLAGES,
                context.randomState(),
                context.seed(),
                chunkpos.getMinBlockX(),
                chunkpos.getMinBlockZ(),
                2
        ); //Makes the structure only spawn above 120 blocks

    }


    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        if (!Moyai.extraSpawnChecks(context))
            return Optional.empty();
        int startY = this.START_HEIGHT.sample(context.random(),new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        ChunkPos chunkpos = context.chunkPos();
        BlockPos blockpos = new BlockPos(chunkpos.getMinBlockX(),startY,chunkpos.getMinBlockZ());
        Optional<GenerationStub>structurePiecesGenerator = JigsawPlacement.addPieces(context, this.STARTPOOL, this.JIGSAW_ORIGIN, this.SIZE, blockpos, false, this.PROJECT_START_HEIGHT_MAP, this.MAX_DISTANCE_FROM_CENTER);
        return structurePiecesGenerator;
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.MOYAI.get();
    }




}
