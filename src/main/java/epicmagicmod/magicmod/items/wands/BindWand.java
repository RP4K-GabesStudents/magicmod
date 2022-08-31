package epicmagicmod.magicmod.items.wands;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.logging.Logger;

public class BindWand extends WandParent{

    private final int RayLength = 100;

    LivingEntity A;
    LivingEntity B;

    Player ply;


    public BindWand(Properties properties, int mainManaUsage, int altManaUsage, int level) {
        super(properties, mainManaUsage, altManaUsage, level);
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
            if(A.isDeadOrDying() || B.isDeadOrDying() || (lvl == 1  && ply.isDeadOrDying()))
            {
                Logger.getAnonymousLogger().info("Killed entities");
                A.kill();
                B.kill();

                A = null;
                B = null;

                if(lvl == 1 ) {
                    ply.kill();
                    ply = null;
                }

                setDamage(pStack, (int)(getDamage(pStack) * 1.25f));
            }
        }
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        A = null;
        B = null;
        return super.onDroppedByPlayer(item, player);
    }

    @Override
    public boolean mainAbility(Level level, Player player) {
        LivingEntity target = getLookAtTarget(level, player, RayLength, false);

        if(target != null) {

            if(!CanCast(target))
            {
                player.sendSystemMessage(Component.literal("THIS WAND IS NOT POWERFUL ENOUGH TO BIND TO: " + target.getDisplayName().getString()));
                return false;
            }
            if(lvl == 1)
            {
                ply = player;
                player.sendSystemMessage(Component.literal("Set target to: " + target.getDisplayName().getString() + " AND YOU DUE TO LOW POWER WAND"));
            }
            else
            {
                player.sendSystemMessage(Component.literal("Set target to: " + target.getDisplayName().getString()));
            }
            A = target;

            if(A instanceof Player targetPly)
            {
                targetPly.sendSystemMessage(Component.literal(player.getDisplayName().getString() + " has bound your soul"));
            }

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
        LivingEntity target = getLookAtTarget(level, player, RayLength, false);
        if(target != null) {

            if(!CanCast(target))
            {
                player.sendSystemMessage(Component.literal("THIS WAND IS NOT POWERFUL ENOUGH TO BIND TO: " + target.getDisplayName().getString()));
                return false;
            }
            if(lvl == 1)
            {
                ply = player;
                player.sendSystemMessage(Component.literal("Set target to: " + target.getDisplayName().getString() + " AND YOU DUE TO LOW POWER WAND"));
            }
            else
            {
                player.sendSystemMessage(Component.literal("Set target to: " + target.getDisplayName().getString()));
            }
            B = target;

            if(B instanceof Player targetPly)
            {
                targetPly.sendSystemMessage(Component.literal(player.getDisplayName().getString() + " has bound your soul"));
            }
            if(A != null)
            {
                player.sendSystemMessage(Component.literal(A.getDisplayName().getString() + " is now linked to " + B.getDisplayName().getString()));
            }
            return true;
        }

        return false;
    }



}
