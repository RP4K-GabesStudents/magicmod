package epicmagicmod.magicmod.items.wands;


import epicmagicmod.magicmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

public class IceWand extends WandParent{


    final double rayLength = 100;

    final int mainDuration;
    final int altDuration;

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
                Blocks.BREWING_STAND,
                Blocks.BEDROCK,
                Blocks.END_PORTAL_FRAME,
                Blocks.OBSIDIAN,
                Blocks.END_PORTAL,
                Blocks.NETHER_PORTAL
        };

    List<SavedBlocks> savedBlocks = new ArrayList<>();

    public class SavedBlocks
    {
        int duration;
        Stack<BlockPos> oldPos;
        Stack<BlockState> old;
        public SavedBlocks(int duration, Stack<BlockPos> oldPos, Stack<BlockState> old)
        {
            this.duration = duration;
            this.oldPos = oldPos;
            this.old = old;
        }
    }




    public IceWand(Properties properties, int mainManaUsage, int altManaUsage, int level, int mainDuration, int altDuration) {
        super(properties, mainManaUsage, altManaUsage, level);
        this.mainDuration = mainDuration;
        this.altDuration = altDuration;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }

    @Override
    public boolean mainAbility(Level level, Player player) {
        //(x,y,z,x,y,z) middle is the player
        int f = lvl * 2;
        BlockPos playerPos = player.blockPosition();

        AABB area = new AABB(playerPos.getX()-f, playerPos.getY()-1, playerPos.getZ()-f, playerPos.getX()+f, playerPos.getY()+2, playerPos.getZ()+f);

        for(Entity e : level.getEntities(player, area)){

            if (e instanceof LivingEntity living){
             //   player.sendSystemMessage(Component.literal("we did a different thing hopefully to" + living.getName()));

                living.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, mainDuration, lvl * 2), living);
                if(lvl == 3)
                {
                    living.forceAddEffect(new MobEffectInstance(MobEffects.BLINDNESS, mainDuration, lvl), living);
                }
            }

        }
        Stack<BlockState> blockStates = new Stack<>();
        HashSet<BlockPos> pos = new HashSet<>(BuildWall(player.position().add(new Vec3(0,f - 1,0)), new Vec3(0,-1,0), f + 1));
        //investigate manhattan block pos
        CreateBlockSet(level, blockStates,  pos, mainDuration);
        return blockStates.size() != 0;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {

        if(!player.level.isClientSide()) {
           // Logger.getAnonymousLogger().info("Removing ALL blocks");
            for (int x = savedBlocks.size()-1; x >= 0; x--) {
                SavedBlocks s = savedBlocks.get(x);
              //  Logger.getAnonymousLogger().info("Checking:  " + s.duration +" - " + s.old.size());
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
               // Logger.getAnonymousLogger().info("Checking:  " + s.duration );
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
       // Logger.getAnonymousLogger().info("Attempting Destroy");
        while(s.oldPos.size() > 0)
        {
            BlockPos pos = s.oldPos.pop();
            BlockState state = s.old.pop();
            Block B = level.getBlockState(pos).getBlock();
            if(B == Blocks.PACKED_ICE ) {
                //Logger.getAnonymousLogger().info("Settings block at (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ") to: " + s.old.get(i).getBlock().getName() + " --> " + i);
                level.setBlockAndUpdate(pos, state);
                i++;
            }
        }
        savedBlocks.remove(s);
    }

    @Override
    public boolean altAbility(Level level, Player player) {
        //Player other = (Player) GetLookAtTarget(level, player, rayLength,true);
        LivingEntity other = getLookAtTarget(level, player, rayLength,false); // DEBUG

        if(lvl >= 2)
        {
            other.forceAddEffect(new MobEffectInstance(MobEffects.DIG_SPEED, altDuration, lvl), other);
        }

        if(lvl == 3)
        {
            other.forceAddEffect(new MobEffectInstance(MobEffects.BLINDNESS, altDuration, 1), other);
        }


        if(other != null)
        {
            //1+ x*2 where X is the distance... distance
            //This forces all spheres to be dist of 3,5,7...

            int dist = lvl+1;

            int trueDist = 1 + dist * 2;




            Vec3 playerPos = other.position();



            Stack<BlockState> blockStates = new Stack<>();
            HashSet<BlockPos> blockPoses = new HashSet<>();

            blockPoses.addAll(BuildWall(playerPos, new Vec3(1,0,0), trueDist));
            blockPoses.addAll(BuildWall(playerPos, new Vec3(-1,0,0), trueDist));
            blockPoses.addAll(BuildWall(playerPos, new Vec3(0,1,0), trueDist));
            blockPoses.addAll(BuildWall(playerPos, new Vec3(0,-1,0), trueDist));
            blockPoses.addAll(BuildWall(playerPos, new Vec3(0,0,1), trueDist));
            blockPoses.addAll(BuildWall(playerPos, new Vec3(0,0,-1), trueDist));

            //investigate manhattan block pos
            CreateBlockSet(level, blockStates, blockPoses, altDuration);

            return true;
        }
        return false;
    }

    private void CreateBlockSet(Level level, Stack<BlockState> blockStates, Iterable<BlockPos> blockPoses, int duration) {

        Stack<BlockPos> pos = new Stack<>();


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
                pos.push(blockpos);
                blockStates.push(level.getBlockState(blockpos));

               // Logger.getAnonymousLogger().info("Adding block at (" + blockpos.getX() + ", " + blockpos.getY() + ", " + blockpos.getZ() + ") to: " + level.getBlockState(blockpos).getBlock().getName() + " --> ");

                level.setBlockAndUpdate(blockpos, Blocks.PACKED_ICE.defaultBlockState());
            }
        }
        savedBlocks.add(new SavedBlocks(duration, pos, blockStates));
    }

    private List<BlockPos> BuildWall(Vec3 origin, Vec3 forward, int extent)
    {
        List<BlockPos> wall = new ArrayList<>();

        //1,0,0,
        //0,1,0
        //0,0,1

        //0,1,0
        //0,0,1
        //1,0,0

        //0,0,1
        //1,0,0
        //0,1,0

        Vec3 up = new Vec3(forward.z, forward.x, forward.y);
        Vec3 right = new Vec3(up.z, up.x, up.y);

        for(int x = 0; x < extent * 2 - 3; x++) // 0,1,2,3,4,5,6,7,8,9,10 || 11 total
        {
            int trueX = x-extent + 2; //-5,-4,-3,-2,-1,0,1,2,3,4,5 (10-5)
            int dif= Math.max(Math.abs(trueX) - (extent / 2),0); //2,1,0,0,0,0,0,0,0,1,2
            for(int y = 0; y < extent * 2 - 3; y++) // 0,1,2,3,4,5,6,7,8,9,10 || 11 total
            {
                //If there is a dif, move MID in opposite direction
                int trueY = y-extent + 2; //-5,-4,-3,-2,-1,0,1,2,3,4,5 (10-5)
                int trueDif = dif+ Math.max(Math.abs(trueY) - (extent / 2),0); //2,1,0,0,0,0,0,0,0,1,2
                //DIF is in
                //Right is right and left
                // UP is up and down
                BlockPos added = new BlockPos(origin.add(right.scale(trueX)).add(up.scale(trueY)).add(forward.scale(extent-1-trueDif))); // .add(forward.scale(extent-dif*2))
               wall.add(added);
            }
        }



        return wall;
    }

}
