package epicmagicmod.magicmod.items.wands;

import epicmagicmod.magicmod.block.ShardOreItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class TeleportWand extends WandParent{


    final double rayLength = 100;
    public TeleportWand(Properties properties, int mainManaUsage, int altManaUsage, String name, float level, ShardOreItem.EOreType bound) {
        super(properties, mainManaUsage, altManaUsage,  name,level,bound);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }

    @Override
    public boolean mainAbility(Level level, Player player) {

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
            int lvl = getLVL(player.getItemInHand(InteractionHand.MAIN_HAND));
            //IF RAY HIT SOMETHING
            Vec3 hitLocation = rayHit.getLocation();
            player.teleportTo(hitLocation.x, hitLocation.y, hitLocation.z);

            if(lvl <= 1)
            {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200 * (lvl + 1), lvl));
            }
            if(lvl >= 2)
            {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 200* (lvl + 1), lvl));
            }


            return true;
        }
        return false;
    }

    @Override
    public boolean altAbility(Level level, Player player) {
        LivingEntity target = getLookAtTarget(level, player, rayLength, false);
        if (target != null)
        {
            Vec3 playerPos = player.position();
            player.teleportTo(target.getX(), target.getY(), target.getZ());
            player.sendSystemMessage(Component.literal("TELEPORT HIT"));
            target.teleportTo(playerPos.x, playerPos.y, playerPos.z);
            int lvl = getLVL(player.getItemInHand(InteractionHand.MAIN_HAND));
            if(lvl <= 1)
            {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200* (lvl + 1), lvl));
            }
            if(lvl <= 2)
            {
                target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200* (lvl + 1), lvl));
            }
            if(lvl >= 2)
            {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 200* (lvl + 1), lvl));
                target.addEffect(new MobEffectInstance(MobEffects.JUMP, 200* (lvl + 1), lvl));
            }

            return true;

        }
        return false;
    }
}
