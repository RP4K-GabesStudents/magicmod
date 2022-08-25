package epicmagicmod.magicmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.logging.Logger;

public class TempIceBlock extends Block {

    public int lifeSpan;
    public BlockState previous;

    public void SetInfo(int lifeSpan, BlockState prv)
    {
        this.lifeSpan = lifeSpan;
        this.previous = prv;
    }

    public TempIceBlock(Properties pProperties) {
        super(pProperties);


    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);
        if(lifeSpan-- <= 0)
        {
            Logger.getAnonymousLogger().info("Done ticking");
            OnDestroy(pLevel, pPos);
        }
        spawnParticles(pLevel, pPos);
    }


    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(level.isClientSide())
            return false;
        Logger.getAnonymousLogger().info("ICE TEMP BLOCK DESTROYED");
        OnDestroy((ServerLevel) level, pos);
        return false;
    }

    private void OnDestroy(ServerLevel level, BlockPos prv)
    {
        if(previous != null) {
            level.setBlock(prv, previous, 2); //send change to clients
        }
        else
        {
            level.setBlock(prv, Blocks.AIR.defaultBlockState(), 2);
        }
    }

    private static void spawnParticles(Level pLevel, BlockPos pPos) {
        double d0 = 0.5625D;
        RandomSource randomsource = pLevel.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = pPos.relative(direction);
            if (!pLevel.getBlockState(blockpos).isSolidRender(pLevel, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                pLevel.addParticle(DustParticleOptions.REDSTONE, (double)pPos.getX() + d1, (double)pPos.getY() + d2, (double)pPos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }

    }
}
