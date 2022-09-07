package epicmagicmod.magicmod.items.wands;

import epicmagicmod.magicmod.block.ShardOreItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public class RandomizerWand extends WandParent{
    public RandomizerWand(Properties properties, int mainManaUsage, int altManaUsage, String name, float costMult, ShardOreItem.EOreType bound) {
        super(properties, mainManaUsage, altManaUsage, name, costMult, bound);
    }

    private EntityType [] passiveMobs = new EntityType[]
        {
                EntityType.COW,
                EntityType.CHICKEN,
                EntityType.BAT,
                EntityType.ALLAY,
                EntityType.BEE,
                EntityType.SQUID,
                EntityType.GLOW_SQUID,
                EntityType.AXOLOTL,
                EntityType.ARMOR_STAND,
                EntityType.ARROW,
                EntityType.SHEEP,
                EntityType.BOAT,
                EntityType.CHEST_BOAT,
                EntityType.CHEST_MINECART
        };

    private EntityType [] hostileMobs = new EntityType[]
            {
                    EntityType.ZOMBIE,
                    EntityType.WITHER_SKELETON,
                    EntityType.WITHER,
                    EntityType.SKELETON,
                    EntityType.BLAZE,
                    EntityType.ENDER_DRAGON,
                    EntityType.ENDERMAN,
                    EntityType.ENDERMITE,
                    EntityType.DRAGON_FIREBALL,
                    EntityType.FIREBALL,
                    EntityType.GIANT,
                    EntityType.CREEPER,
                    EntityType.WARDEN,
                    EntityType.CAVE_SPIDER
            };


    @Override
    public boolean mainAbility(Level level, Player player) {

        List<Entity> ents = getEntitiesInAOE(level, player, 100, 1 + getLVL(player.getItemInHand(InteractionHand.MAIN_HAND)));
        if(ents.size() == 0)
            return false;
        for (Entity e : ents) {
            if(e instanceof LivingEntity le && !(le instanceof Player))
            {
                passiveMobs[RandomSource.create().nextInt(passiveMobs.length)].create(level);
                e.remove(Entity.RemovalReason.DISCARDED);
            }
        }
        return true;
    }

    @Override
    public boolean altAbility(Level level, Player player) {
        List<Entity> ents = getEntitiesInAOE(level, player, 100, 1 + getLVL(player.getItemInHand(InteractionHand.MAIN_HAND)));
        if(ents.size() == 0)
            return false;
        for (Entity e : ents) {
            if(e instanceof LivingEntity le && !(le instanceof Player))
            {
                hostileMobs[RandomSource.create().nextInt(passiveMobs.length)].create(level);
                e.remove(Entity.RemovalReason.DISCARDED);
            }
        }
        return true;
    }
}
