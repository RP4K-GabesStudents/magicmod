package epicmagicmod.magicmod.items.wands;

import com.mojang.math.Vector3d;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LightningWand extends WandParent{


    public LightningWand(Properties p_41383_) {
        super(p_41383_, 75);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }


    @Override
    public void ability(Level level, Player player) {

        double x = player.getEyePosition().x;
        double y = player.getEyePosition().y;
        double z = player.getEyePosition().z;

        //player.setPos(x, y ,z);


    }
}
