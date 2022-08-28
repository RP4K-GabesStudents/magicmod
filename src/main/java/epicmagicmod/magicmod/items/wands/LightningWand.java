package epicmagicmod.magicmod.items.wands;

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

public class LightningWand extends WandParent{

    final double rayLength = 100;
    public LightningWand(Properties properties) {
        super(properties, 7500, 7500);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {


        activate(level, player);


        return super.use(level, player, hand);
    }


    @Override
    public boolean mainAbility(Level level, Player player) {

        LivingEntity le = getLookAtTarget(level, player, rayLength, false);
        if(le != null) {
            //IF RAY HIT SOMETHING
            Vec3 hitLocation = le.getPosition(1f);

            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            lightningBolt.setPos(hitLocation);

            level.addFreshEntity(lightningBolt);
            return true;
        }
        else
        {
            Vec3 playerRotation = player.getViewVector(1f).normalize();
            Vec3 rayPath = playerRotation.scale(rayLength);

            Vec3 from = player.getEyePosition(0);
            Vec3 to = from.add(rayPath);

            //CREATE THE RAY
            ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
            //CAST THE RAY
            BlockHitResult rayHit = level.clip(rayCtx);

            if(rayHit.getType() != HitResult.Type.MISS)
            {
                Vec3 hitLocation = rayHit.getLocation();

                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt.setPos(hitLocation);

                level.addFreshEntity(lightningBolt);
                return true;
            }

        }
        return false;
    }

    private final int range = 4;

    @Override
    public boolean altAbility(Level level, Player player) {

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



        return ret;
    }
}
