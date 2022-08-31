package epicmagicmod.magicmod.items.wands;

import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FireWand extends WandParent{


    public FireWand(Properties properties, int mainManaUsage, int altManaUsage, int level) {
        super(properties, mainManaUsage, altManaUsage, level);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }

    @Override
    public boolean mainAbility(Level level, Player player) {

        int randomExplosion = RandomSource.createNewThreadLocalInstance().nextInt(5,10);

        LargeFireball fireball = new LargeFireball(level, player, 0, 0, 0, randomExplosion);
        fireball.setPos(player.position());
        level.addFreshEntity(fireball);
        fireball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 5, 1.0f);

        return true;

    }

    @Override
    public boolean altAbility(Level level, Player player) {
        return false;
    }
}
