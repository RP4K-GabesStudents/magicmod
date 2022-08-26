package epicmagicmod.magicmod.items.wands;


import epicmagicmod.magicmod.block.ModBlocks;
import epicmagicmod.magicmod.block.TempIceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class IceWand extends WandParent{


    private static Block [] unreplaceableBlocks = new Block[]
        {
                Blocks.PACKED_ICE,
                ModBlocks.MANA_EXTRACTOR.get(),
                Blocks.CHEST,
                Blocks.TRAPPED_CHEST,
                Blocks.SHULKER_BOX,
                Blocks.FURNACE,
                Blocks.BLAST_FURNACE,
                Blocks.CAMPFIRE,
                Blocks.SOUL_CAMPFIRE,
                Blocks.BREWING_STAND
        };

    List<SavedBlocks> savedBlocks = new ArrayList<>();

    public class SavedBlocks
    {
        int duration;
        Iterable<BlockPos> oldPos;
        List<BlockState> old;
        public SavedBlocks(int duration, Iterable<BlockPos> oldPos, List<BlockState> old)
        {
            this.duration = duration;
            this.oldPos = oldPos;
            this.old = old;
        }
    }




    public IceWand(Properties properties) {
        super(properties, 2500, 2500);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }

    @Override
    public void mainAbility(Level level, Player player) {
        //(x,y,z,x,y,z) middle is the player
        double f = 4;
        BlockPos playerPos = player.blockPosition();

        AABB area = new AABB(playerPos.getX()-f, playerPos.getY()-1.0D, playerPos.getZ()-f, playerPos.getX()+f, playerPos.getY()+2.0D, playerPos.getZ()+f);

        for(Entity e : level.getEntities(player, area)){

            if (e instanceof LivingEntity living){
                player.sendSystemMessage(Component.literal("we did a different thing hopefully to" + living.getName()));

                living.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2), living);
                living.forceAddEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 2), living);
            }

        }
        List<BlockState> blockStates = new ArrayList<>();
        Iterable<BlockPos> blockPoses = BlockPos.betweenClosed(playerPos.offset((-f), -1.0D, (-f)), playerPos.offset(f, -1.0D, f));
        //investigate manhattan block pos
        for(BlockPos blockpos : blockPoses) {

            Block B = level.getBlockState(blockpos).getBlock();
            boolean allowed = true;
            for(Block x : unreplaceableBlocks)
            {
                if(B == x)
                {
                    allowed = false;
                    break;
                }
            }

            if(allowed)
            {
                blockStates.add(level.getBlockState(blockpos));

                Logger.getAnonymousLogger().info("Adding block at (" + blockpos.getX() + ", " + blockpos.getY() + ", " + blockpos.getZ() + ") to: " + level.getBlockState(blockpos).getBlock().getName() + " --> ");

                level.setBlockAndUpdate(blockpos, Blocks.PACKED_ICE.defaultBlockState());
            }
        }
        savedBlocks.add(new SavedBlocks(60,blockPoses, blockStates));
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {

        if(!player.level.isClientSide()) {
            Logger.getAnonymousLogger().info("Removing ALL blocks");
            for (int x = savedBlocks.size()-1; x >= 0; x--) {
                SavedBlocks s = savedBlocks.get(x);
                Logger.getAnonymousLogger().info("Checking:  " + s.duration +" - " + s.old.size());
                ResetBlocks(player.getLevel(), s);
            }
        }


        return super.onDroppedByPlayer(item, player);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if(!pLevel.isClientSide()) {
            for (int x = savedBlocks.size()-1; x >= 0; x--) {
                SavedBlocks s = savedBlocks.get(x);
                Logger.getAnonymousLogger().info("Checking:  " + s.duration );
                //s.old.get()
                if (s.duration-- <= 0) {
                    ResetBlocks(pLevel, s);
                }
            }
        }
    }

    public void ResetBlocks(Level level, SavedBlocks s)
    {
        int i =0;
        Logger.getAnonymousLogger().info("Attempting Destroy");

        for (BlockPos pos : s.oldPos) {
            Block B = level.getBlockState(pos).getBlock();
            if(B == Blocks.PACKED_ICE) {
                Logger.getAnonymousLogger().info("Settings block at (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ") to: " + s.old.get(i).getBlock().getName() + " --> " + i);
                level.setBlockAndUpdate(pos, s.old.get(i));
                i++;
            }
        }
        savedBlocks.remove(s);
    }

    @Override
    public void altAbility(Level level, Player player) {

    }
}
