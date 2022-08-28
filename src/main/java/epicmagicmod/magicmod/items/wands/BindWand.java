package epicmagicmod.magicmod.items.wands;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.logging.Logger;

public class BindWand extends WandParent{

    private final int RayLength = 100;

    LivingEntity A;
    LivingEntity B;


    public BindWand(Properties properties) {
        super(properties, 500, 500);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        activate(level, player);
        return super.use(level, player, hand);
    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if(!pLevel.isClientSide() && A != null && B != null)
        {
            if(A.isDeadOrDying() || B.isDeadOrDying())
            {
                Logger.getAnonymousLogger().info("Killed entities");
                A.kill();
                B.kill();
                A = null;
                B = null;
                setDamage(pStack, (int)(getDamage(pStack) * 1.25f));
            }
        }
    }

    @Override
    public boolean mainAbility(Level level, Player player) {
        LivingEntity setA = getLookAtTarget(level, player, RayLength, false);

        if(setA != null) {
            player.sendSystemMessage(Component.literal("Set target to: " + setA.getDisplayName().getString()));
            A = setA;
            if(B != null)
            {
                player.sendSystemMessage(Component.literal(A.getDisplayName().getString() + " is now linked to " + B.getDisplayName().getString()));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean altAbility(Level level, Player player) {
        LivingEntity setB = getLookAtTarget(level, player, RayLength, false);

        if(setB != null) {
            player.sendSystemMessage(Component.literal("Set target to: " + setB.getDisplayName().getString()));
            B = setB;
            if(A != null)
            {
                player.sendSystemMessage(Component.literal(A.getDisplayName().getString() + " is now linked to " + B.getDisplayName().getString()));
            }
            return true;
        }

        return false;
    }
}
