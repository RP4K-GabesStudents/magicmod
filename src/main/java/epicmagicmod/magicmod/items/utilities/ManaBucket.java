package epicmagicmod.magicmod.items.utilities;

import epicmagicmod.magicmod.block.ManaLiquidBlock;
import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;
import java.util.logging.Logger;

public class ManaBucket extends BucketItem {

    private final int DRINK_DURATION; // Honey is 40, milk is 32
    private final ManaLiquidBlock BLOCK;

    public ManaBucket(Supplier<? extends Fluid> supplier, Properties builder, int drink_duration, ManaLiquidBlock BLOCK) {
        super(supplier, builder);
        DRINK_DURATION = drink_duration;
        this.BLOCK = BLOCK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        //if (!pLevel.isClientSide) { cool honey potion logic
        //    pEntityLiving.removeEffect(MobEffects.POISON);
        // }
        if (pEntityLiving instanceof ServerPlayer serverplayer) {
            //if (!pLevel.isClientSide) {
            pEntityLiving.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(playerMana -> {
                for (MobEffectInstance me: BLOCK.EFFECTS)
                {
                   serverplayer.addEffect(new MobEffectInstance(me.getEffect(), me.getDuration(), me.getAmplifier()), serverplayer);
                }
                playerMana.augmentMana(BLOCK.MANA_CHANGE * 1000, serverplayer);
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
        //Allow all plays to place mana blocks...
        if(hand == InteractionHand.OFF_HAND ^ player.isCrouching()) {
            return super.use(level, player, hand);
        }

        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}
