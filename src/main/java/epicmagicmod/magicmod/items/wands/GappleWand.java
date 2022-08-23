package epicmagicmod.magicmod.items.wands;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GappleWand extends WandParent{


    public GappleWand(Properties p_41383_) {
        super(p_41383_, 50000);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }

    @Override
    public void ability(Level level, Player player) {

        player.forceAddEffect(new MobEffectInstance(MobEffects.SATURATION, 100, 5), player);

    }
}
