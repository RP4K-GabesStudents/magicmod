package epicmagicmod.magicmod.items.wands;

import epicmagicmod.magicmod.block.ShardOreItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class GappleWand extends WandParent{


    public GappleWand(Properties properties, int mainManaUsage, int altManaUsage, String name, float level, ShardOreItem.EOreType bound ) {
        super(properties, mainManaUsage, altManaUsage, name, level,bound);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }

    @Override
    public boolean mainAbility(Level level, Player player) {
        List<Player> list = new ArrayList<>();
        list.add(player);
        return AddEffects(player, list);
    }

    @Override
    public boolean altAbility(Level level, Player player) {
        int lvl = getLVL(player.getItemInHand(InteractionHand.MAIN_HAND));
        return AddEffects(player, getPlayersInAOE(level, player, 50, 3 + lvl));
    }

    private boolean AddEffects(Player player, List<Player> entityList)
    {
        int lvl = getLVL(player.getItemInHand(InteractionHand.MAIN_HAND));
        if(entityList.size() == 0)
            return false;

        for (Player ply : entityList)
        {
            if(lvl >= 0)
            {
                ply.addEffect(new MobEffectInstance(MobEffects.SATURATION, 400 * lvl, lvl));
                ply.getFoodData().setFoodLevel(20);
            }
            if(lvl >= 1)
            {
                ply.heal(ply.getMaxHealth()-ply.getHealth());
                ply.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300 * lvl, lvl));
            }
            if(lvl >= 2)
            {
                ply.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300 * lvl, lvl));
                ply.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300 * lvl, lvl));
                ply.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 300 * lvl, lvl));
            }
        }
        return true;
    }

}
