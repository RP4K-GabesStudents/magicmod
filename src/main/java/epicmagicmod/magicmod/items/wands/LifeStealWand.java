package epicmagicmod.magicmod.items.wands;

import epicmagicmod.magicmod.block.ShardOreItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class LifeStealWand extends WandParent{
    public LifeStealWand(Properties properties, int mainManaUsage, int altManaUsage, String name, float costMult, ShardOreItem.EOreType bound) {
        super(properties, mainManaUsage, altManaUsage, name, costMult, bound);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }

    @Override
    public boolean mainAbility(Level level, Player player) {
        //(x,y,z,x,y,z) middle is the player
        int lvl = getLVL(player.getItemInHand(InteractionHand.MAIN_HAND));
        int l = (lvl+1) * 2;
        BlockPos playerPos = player.blockPosition();

        AABB area = new AABB(playerPos.getX()-l, playerPos.getY()-1, playerPos.getZ()-l, playerPos.getX()+l, playerPos.getY()+2, playerPos.getZ()+l);
        List<Entity>ents = level.getEntities(player, area);
        if (ents.size() == 0){
            return false;
        }
        for(Entity e : ents){

            if (e instanceof LivingEntity living){
                //   player.sendSystemMessage(Component.literal("we did a different thing hopefully to" + living.getName()));

                living.setHealth(living.getHealth() - l);
                player.heal(ents.size()*2);

            }

        }
        return true;
    }

    @Override
    public boolean altAbility(Level level, Player player) {

        int lvl = getLVL(player.getItemInHand(InteractionHand.MAIN_HAND));
        int l = (lvl+1) * 2;
        BlockPos playerPos = player.blockPosition();

        AABB area = new AABB(playerPos.getX()-l, playerPos.getY()-1, playerPos.getZ()-l, playerPos.getX()+l, playerPos.getY()+2, playerPos.getZ()+l);
        List<Entity>ents = level.getEntities(player, area);
        if (ents.size() == 0){
            return false;
        }
        for(Entity e : ents){

            if (e instanceof LivingEntity living){
                //   player.sendSystemMessage(Component.literal("we did a different thing hopefully to" + living.getName()));

                living.forceAddEffect(new MobEffectInstance(MobEffects.POISON, 15, 2), living);
                living.hurt(DamageSource.OUT_OF_WORLD, 5f);

            }

        }
        return true;

    }
}
