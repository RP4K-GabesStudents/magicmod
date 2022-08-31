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

import java.util.List;

//Doms wand
public class ThrustWand extends WandParent{

    final int RayLength = 100;
    final int Force;

    final int Radius;

    public ThrustWand(Properties properties, int mainManaUsage, int altManaUsage, int level, int radius, int force) {
        super(properties, mainManaUsage, altManaUsage, level);
        this.Radius = radius;
        this.Force = force;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }


    @Override
    public boolean mainAbility(Level level, Player player) {
        List<Entity> targets = getEntitiesInAOE(level, player, RayLength, false, Radius);

        if(lvl == 1)
        {
            targets.add(player);
        }
        for (Entity e: targets)
        {
            e.setDeltaMovement(e.getDeltaMovement().add(new Vec3(0, Force,0)));
        }
        return targets.size() > 0;
    }

    @Override
    public boolean altAbility(Level level, Player player) {
        List<Entity> targets = getEntitiesInAOE(level, player, RayLength, false, Radius);
        if(lvl == 1)
        {
            targets.add(player);
        }
        for (Entity e: targets)
        {
            e.setDeltaMovement(e.getDeltaMovement().add(new Vec3(0, -Force * 2,0)));
            if(e instanceof LivingEntity le)
            {
                le.forceAddEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 80, lvl), le);
            }
        }
        return targets.size() > 0;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity le)
        {
            le.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 80, lvl), le);
            le.forceAddEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 80, lvl), le);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}
