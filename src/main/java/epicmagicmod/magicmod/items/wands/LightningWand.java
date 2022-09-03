package epicmagicmod.magicmod.items.wands;

import epicmagicmod.magicmod.block.ShardOreItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class LightningWand extends WandParent{

    final double rayLength = 100;
    final int aoe;

    int curTime;

    int funcID = -1;
    int amt = 0;

    Player ply;

    public LightningWand(Properties properties, int mainManaUsage, int altManaUsage, String name, float level, ShardOreItem.EOreType bound, int aoe) {
        super(properties, mainManaUsage, altManaUsage,  name,level,bound);
        this.aoe=aoe;
        curTime = 0;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {


        activate(level, player);


        return super.use(level, player, hand);
    }

    //RESTRIKE LOGIC
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if(!pLevel.isClientSide()) {
            if (ply != null &&  funcID != -1 && curTime-- <= 0 && amt != 0) {
                curTime = 5;
                switch (funcID)
                {
                    case 0 -> mainAbility(pLevel, ply);
                    case 1 -> altAbility(pLevel, ply);
                }
                if(--amt == 0)
                {
                    funcID = -1;
                }
            }
        }

    }

    @Override
    public boolean mainAbility(Level level, Player player) {
        int lvl = getLVL(player.getItemInHand(InteractionHand.MAIN_HAND));
        ply = player;
        boolean check = false;
        for (Entity e : getEntitiesInAOE(level, player, rayLength, aoe + lvl)) {
            if (e instanceof LivingEntity le) {
                //IF RAY HIT SOMETHING
                for(int i = 0; i < aoe+ lvl; i ++) {
                    Vec3 hitLocation = le.getPosition(1f);
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt.setPos(hitLocation);
                    level.addFreshEntity(lightningBolt);
                }
             check = true;
            }
        }
        if(check) {
            if (funcID == -1) {
                funcID = 0;
                amt = Math.max(0, lvl-1);
                curTime = 5;
            }
            return true;
        }
        Vec3 playerRotation = player.getViewVector(1f).normalize();
        Vec3 rayPath = playerRotation.scale(rayLength);

        Vec3 from = player.getEyePosition(0);
        Vec3 to = from.add(rayPath);

        //CREATE THE RAY
        ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
        //CAST THE RAY
        BlockHitResult rayHit = level.clip(rayCtx);

        if(rayHit.getType() != HitResult.Type.MISS) {

            Vec3 hitLocation = new Vec3(rayHit.getLocation().x - (aoe+ lvl)/2, rayHit.getLocation().y, rayHit.getLocation().z - aoe/2);

            for (int x = 0; x < aoe+ lvl; x++)
            {

                for (int y = 0; y < aoe+ lvl; y++)
                {
                    Vec3 trueHit = hitLocation.add(new Vec3(x, 0, y));

                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt.setPos(trueHit);
                    level.addFreshEntity(lightningBolt);

                }
            }






            if(funcID==-1) {
                funcID = 0;
                amt = Math.max(0, lvl-1);
                curTime = 5;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean altAbility(Level level, Player player) {
        int lvl = getLVL(player.getItemInHand(InteractionHand.MAIN_HAND));
        ply = player;
        int range = (aoe + lvl) * 3;
        Vec3 p = player.position();
        AABB inRange = new AABB(p.x - range, p.y - range, p.z - range, p.x + range, p.y + range, p.z + range);
        boolean ret = false;
        for (Entity e : level.getEntities(player, inRange))
        {
            if(e instanceof LivingEntity le)
            {
                Vec3 hitLocation = le.getPosition(0f);

                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt.setPos(hitLocation);
                ret = true;
                level.addFreshEntity(lightningBolt);
            }
        }
        if(ret && funcID==-1) {
            funcID = 1;
            amt = Math.max(0, lvl-1);
            curTime = 5;
        }
        return ret;
    }
}
