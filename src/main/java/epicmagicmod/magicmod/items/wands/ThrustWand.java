package epicmagicmod.magicmod.items.wands;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

//Doms wand
public class ThrustWand extends WandParent{

    final int RayLength = 100;
    final int Force = 5;

    public ThrustWand(Properties properties) {
        super(properties, 200, 200);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }


    @Override
    public boolean mainAbility(Level level, Player player) {
        LivingEntity target = getLookAtTarget(level, player, RayLength, false);

        if(target != null)
        {
            target.setDeltaMovement(target.getDeltaMovement().add(new Vec3(0, Force,0)));
            return true;
        }

        return false;
    }

    @Override
    public boolean altAbility(Level level, Player player) {
        LivingEntity target = getLookAtTarget(level, player, RayLength, false);

        if(target != null)
        {
            target.setDeltaMovement(target.getDeltaMovement().add(new Vec3(0, -Force,0)));
            return true;
        }

        return false;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity le)
        {
            le.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 80, 2), le);
            le.forceAddEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 80, 2), le);

        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}
