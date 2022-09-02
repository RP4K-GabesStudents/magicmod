package epicmagicmod.magicmod.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.IPlantable;

import java.util.logging.Logger;

public class EpicDirt extends Block {


    public EpicDirt(Properties pProperties) {
        super(pProperties);
        Logger.getAnonymousLogger().info("Loaded Epic Dirt");

    }

    @Override
    public MaterialColor getMapColor(BlockState state, BlockGetter level, BlockPos pos, MaterialColor defaultColor) {
        return MaterialColor.GRASS;
    }

    @Override
    public MaterialColor defaultMaterialColor() {
        return MaterialColor.GRASS;
    }

    @Override
    protected Block asBlock() {
        return Blocks.GRASS_BLOCK;
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
        Launch( pEntity,  pLevel,  pPos);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        super.stepOn(pLevel, pPos, pState, pEntity);
        Launch( pEntity,  pLevel,  pPos);
    }

    @OnlyIn(Dist.CLIENT)
    public static void blockColorLoad(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((bs, world, pos, index) ->
                (world != null && pos != null) ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0.5D, 1.0D), new Block[] { ModBlocks.GRASS.get() });
    }

    /*
    @OnlyIn(Dist.CLIENT)
    public static void registerRenderLayer() {
        ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.GRASS.get(), renderType -> (renderType == RenderType.cutout()));
    }
*/

    private void Launch(Entity pEntity, Level pLevel, BlockPos pPos)
    {
        if (pEntity instanceof Player player) {
            player.forceAddEffect(new MobEffectInstance(MobEffects.JUMP,50,50), player);
            //player.sendSystemMessage(Component.literal("Trolled -- Step").withStyle(ChatFormatting.RED));
            player.setDeltaMovement(0,  5,  0);


            pLevel.setBlockAndUpdate(pPos, Blocks.GRASS_BLOCK.defaultBlockState());
        }
    }



}
