package epicmagicmod.magicmod.items.wands;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class IceWand extends WandParent{


    public IceWand(Properties p_41383_) {
        super(p_41383_, 2500);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }

    @Override
    public void ability(Level level, Player player) {
        //(x,y,z,x,y,z) middle is the player
        double f = 4;
        BlockPos playerPos = player.blockPosition();
        BlockState blockstate = Blocks.ICE.defaultBlockState();

        AABB area = new AABB(playerPos.getX()-f, playerPos.getY()-1.0D, playerPos.getZ()-f, playerPos.getX()+f, playerPos.getY()+2.0D, playerPos.getZ()+f);

        for(Entity e : level.getEntities(player, area)){

            if (e instanceof LivingEntity living){
                player.sendSystemMessage(Component.literal("we did a different thing hopefully to" + living.getName()));

                living.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2), living);
                living.forceAddEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 2), living);
            }

        }

        //investigate manhattan block pos
        for(BlockPos blockpos : BlockPos.betweenClosed(playerPos.offset((-f), -1.0D, (-f)), playerPos.offset(f, -1.0D, f))) {
            level.setBlockAndUpdate(blockpos, blockstate);
        }
    }
}
