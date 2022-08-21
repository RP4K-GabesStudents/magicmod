package epicmagicmod.magicmod.items.wands;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class TeleportWand extends WandParent{


    public TeleportWand(Properties p_41383_) {
        super(p_41383_, 100);
    }

    @Override
    public void ability(Level level, Player player) {

        double x = player.getEyePosition().x;
        double y = player.getEyePosition().y;
        double z = player.getEyePosition().z;

        player.setPos(x, y ,z);
    }
}
