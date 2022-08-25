package epicmagicmod.magicmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.logging.Logger;

public class TempIceBlock extends Block {

    private int lifeSpan;

    private TempIceBlock(Properties pProperties, int lifeSpan) {
        super(pProperties);
        this.lifeSpan = lifeSpan;
    }


    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);
        if(lifeSpan-- <= 0)
        {
            OnDestroy();
        }
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        super.destroy(pLevel, pPos, pState);
        Logger.getAnonymousLogger().info("");
    }

    private void OnDestroy()
    {

    }
}
