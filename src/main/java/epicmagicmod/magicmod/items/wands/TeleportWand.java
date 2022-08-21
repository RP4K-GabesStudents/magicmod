package epicmagicmod.magicmod.items.wands;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.sound.sampled.Clip;

public class TeleportWand extends WandParent{


    public TeleportWand(Properties p_41383_) {
        super(p_41383_, 100);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }

    @Override
    public void ability(Level level, Player player) {

        //RAY END POINT - TO WHERE IT WILL TRAVEL TO
        Double rayLength = new Double(100);
        Vec3 playerRotation = player.getViewVector(0);
        Vec3 rayPath = playerRotation.scale(rayLength);

        //RAY START AND END POINTS
        Vec3 from = player.getEyePosition(0);
        Vec3 to = from.add(rayPath);

        //CREATE THE RAY
        ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null);
        //CAST THE RAY
        BlockHitResult rayHit = level.clip(rayCtx);

        //CHECK THE RESULTS
        if (rayHit.getType() == BlockHitResult.Type.MISS){
            //IF RAY MISSED
        }
        else {
            //IF RAY HIT SOMETHING
            Vec3 hitLocation = rayHit.getLocation();

            player.setPos(hitLocation);

        }

    }
}
