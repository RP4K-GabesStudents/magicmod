package epicmagicmod.magicmod.items.wands;

import epicmagicmod.magicmod.block.ShardOreItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SummoningWand extends WandParent{
    public SummoningWand(Properties properties, int mainManaUsage, int altManaUsage, String name, float costMult, ShardOreItem.EOreType bound) {
        super(properties, mainManaUsage, altManaUsage, name, costMult, bound);
    }

    @Override
    public boolean mainAbility(Level level, Player player) {
        return false;
    }

    @Override
    public boolean altAbility(Level level, Player player) {
        return false;
    }
}
