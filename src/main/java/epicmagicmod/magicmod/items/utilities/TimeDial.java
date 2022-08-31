package epicmagicmod.magicmod.items.utilities;

import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.commands.Commands;
import net.minecraft.server.commands.TimeCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TimeDial extends Item {
    public TimeDial(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide())
        {
            pPlayer.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(playerMana ->
            {
                if(playerMana.getMana() >= 2500 || pPlayer.isCreative()) {
                    if (pPlayer.isCrouching())
                        TimeCommand.setTime(pPlayer.createCommandSourceStack(), 16000);
                    else
                        TimeCommand.setTime(pPlayer.createCommandSourceStack(), 800);
                    playerMana.augmentMana(2500, (ServerPlayer) pPlayer);
                }
            });


        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
