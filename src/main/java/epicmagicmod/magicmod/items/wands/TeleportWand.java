package epicmagicmod.magicmod.items.wands;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class TeleportWand extends WandParent{


    final double rayLength = 100;
    public TeleportWand(Properties properties) {
        super(properties, 10000, 10000);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }

    @Override
    public void mainAbility(Level level, Player player) {

        //RAY END POINT - TO WHERE IT WILL TRAVEL TO
        Vec3 playerRotation = player.getViewVector(0);
        Vec3 rayPath = playerRotation.scale(rayLength);

        //RAY START AND END POINTS
        Vec3 from = player.getEyePosition(0);
        Vec3 to = from.add(rayPath);


        //CREATE THE RAY
        ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null);
        //CAST THE RAY
        BlockHitResult rayHit = level.clip(rayCtx);
        //CHECK THE RESULTS
        if (rayHit.getType() != BlockHitResult.Type.MISS){
            //IF RAY HIT SOMETHING
            Vec3 hitLocation = rayHit.getLocation();

            player.teleportTo(hitLocation.x, hitLocation.y, hitLocation.z);

        }
    }

    @Override
    public void altAbility(Level level, Player player) {

        //RAY END POINT - TO WHERE IT WILL TRAVEL TO
        Vec3 playerRotation = player.getViewVector(0);
        Vec3 rayPath = playerRotation.scale(rayLength);

        //RAY START AND END POINTS
        Vec3 from = player.getEyePosition(0);
        Vec3 to = from.add(rayPath);


        //CREATE THE RAY
        ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, null);
        //CAST THE RAY
        BlockHitResult rayHit = level.clip(rayCtx);

        //Store the players previous position
        Vec3 playerPos = player.position();

        BlockPos rayPos = rayHit.getBlockPos();

        AABB debug = new AABB(player.getX()-2, player.getY()-2, player.getZ()-2, rayPos.getX()+2, rayPos.getY()+2, rayPos.getZ()+2);

        LivingEntity closestToPlayer = null;
        double distToPlayer = 100000000;
        LivingEntity closestToTarget = null;
        double distToTarget = 100000000;

        for (Entity e : level.getEntities(player, debug))
        {
            if(e instanceof LivingEntity le)
            {
                double pt = le.distanceToSqr(player);
                if(pt < distToPlayer)
                {
                    distToPlayer = pt;
                    closestToPlayer = le;
                }
                pt = le.distanceToSqr(rayPos.getX(), rayPos.getY(), rayPos.getZ());
                if(pt < distToTarget)
                {
                    distToTarget = pt;
                    closestToTarget = le;
                }
            }
        }


        if (rayHit.getType() == BlockHitResult.Type.MISS && closestToPlayer != null) {
            player.teleportTo(closestToPlayer.getX(), closestToPlayer.getY(), closestToPlayer.getZ());
            player.sendSystemMessage(Component.literal("TELEPORT MISS"));
            closestToPlayer.teleportTo(playerPos.x, playerPos.y, playerPos.z);
        }
        else if (closestToTarget != null)
        {
            player.teleportTo(closestToTarget.getX(), closestToTarget.getY(), closestToTarget.getZ());
            player.sendSystemMessage(Component.literal("TELEPORT HIT"));
            closestToTarget.teleportTo(playerPos.x, playerPos.y, playerPos.z);
        }




    }
}
