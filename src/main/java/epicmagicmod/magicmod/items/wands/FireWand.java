package epicmagicmod.magicmod.items.wands;

import epicmagicmod.magicmod.block.ShardOreItem;
import epicmagicmod.magicmod.entity.MyFireball;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FireWand extends WandParent{


    public FireWand(Properties properties, int mainManaUsage, int altManaUsage, String name, float level , ShardOreItem.EOreType bound) {
        super(properties, mainManaUsage, altManaUsage, name, level,bound);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }

    @Override
    public boolean mainAbility(Level level, Player player) {

        //LargeFireball fireball = new LargeFireball(level, player, 0, 0, 0, randomExplosion);
        //fireball.setPos(player.position() + player.eye);
        //level.addFreshEntity(fireball);
        //fireball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 5, 1.0f);
        LaunchEntity(level, player, true);


        return true;

    }

    @Override
    public boolean altAbility(Level level, Player player) {

        LaunchEntity(level, player, false);

        return true;
    }

    private void LaunchEntity(Level level, Player player, boolean breakBlocks)
    {
        int lvl = getLVL(player.getItemInHand(InteractionHand.MAIN_HAND));
        float xRot = player.getViewXRot(0f);
        float yRot = player.getViewYRot(0f);

        float ang = 10; // 25 deg

        int randomExplosion = 0;

        for(int i = 0; i < lvl+1; i++) {
            randomExplosion += RandomSource.createNewThreadLocalInstance().nextInt(1, 4);
        }

        for(int y = -(lvl+1)+1; y < lvl+1; y++)
        {
            //I'm too lazy to do a rotation matrix here. Deal with this.
            float curY = yRot + y * ang;
            MyFireball ball = new MyFireball(level, player, Vec3.ZERO, randomExplosion, breakBlocks);
            //LargeFireball ball = new LargeFireball(level, player,player.position().x, player.position().y, player.position().z, randomExplosion );
            ball.setPos(player.getEyePosition());
            ball.shootFromRotation(player, xRot , curY, 0, 5, 0);
            level.addFreshEntity(ball);
        }
    }

}
