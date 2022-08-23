package epicmagicmod.magicmod.items.utilities;

import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class ManaBucket extends BucketItem {

    private final int DRINK_DURATION; // Honey is 40, milk is 32

    public ManaBucket(Supplier<? extends Fluid> supplier, Properties builder, int drink_duration) {
        super(supplier, builder);
        DRINK_DURATION = drink_duration;
    }
    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        //if (!pLevel.isClientSide) { cool honey potion logic
        //    pEntityLiving.removeEffect(MobEffects.POISON);
        // }
        if (pEntityLiving instanceof ServerPlayer serverplayer) {
            //if (!pLevel.isClientSide) {
            pEntityLiving.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(playerMana -> {
                int prv = playerMana.getMana();
                playerMana.augmentMana(10000, serverplayer);
                pEntityLiving.sendSystemMessage(Component.literal(prv + " --> Added mana: --> " + playerMana.getMana()));
            });
            //}

            pStack.shrink(1);
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, pStack);
            serverplayer.addItem(Items.BUCKET.getDefaultInstance());

            //If no more items, give a bucket... but if more items... give the stack back?
            //if (!pStack.isEmpty())
            //    return pStack;
            //}
        }
        return pStack;
    }

    public int getUseDuration(ItemStack pStack) {
        return DRINK_DURATION;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        if(hand == InteractionHand.OFF_HAND && player.isCreative()) {
            return super.use(level, player, hand);
        }

        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}
