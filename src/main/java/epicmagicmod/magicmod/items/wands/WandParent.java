package epicmagicmod.magicmod.items.wands;

import epicmagicmod.magicmod.mana.PlayerMana;
import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.NoteBlockEvent;

public abstract class WandParent extends Item {


    int manaUsage;
    public WandParent(Properties p_41383_, int manaUsage) {
        super(p_41383_);
        this.manaUsage = manaUsage;
    }


    // this is what all wands must do to shoot
    public final void activate(Level level, Player player){

        if(level.isClientSide()){
            return;
        }
        player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(playerMana -> {

            if (playerMana.getMana() >= manaUsage || player.isCreative()){

                ability(level, player);
                player.sendSystemMessage(Component.literal("we did a thing hopefully"));

                playerMana.augmentMana(-manaUsage, (ServerPlayer) player);
            }


        });



    }



    public abstract void ability(Level level, Player player);




}
