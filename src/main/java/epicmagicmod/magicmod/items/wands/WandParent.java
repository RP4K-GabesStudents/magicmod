package epicmagicmod.magicmod.items.wands;

import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;


public abstract class WandParent extends Item {


    private final int mainManaUsage;
    private final int altManaUsage;
    public WandParent(Properties properties, int mainManaUsage, int altManaUsage) {
        super(properties);
        this.mainManaUsage = mainManaUsage;
        this.altManaUsage = altManaUsage;
    }


    // this is what all wands must do to shoot
    public final void activate(Level level, Player player){

        if(level.isClientSide()){
            return;
        }
        player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(playerMana -> {

            if(!player.isCrouching())
            {
                if (playerMana.getMana() >= mainManaUsage || player.isCreative()){

                    mainAbility(level, player);
                    player.sendSystemMessage(Component.literal("MAIN ATTACK"));

                    playerMana.augmentMana(-mainManaUsage, (ServerPlayer) player);
                }
            }
            else
            {
                if (playerMana.getMana() >= mainManaUsage || player.isCreative()){

                    altAbility(level, player);
                    player.sendSystemMessage(Component.literal("ALT ATTACK"));

                    playerMana.augmentMana(-mainManaUsage, (ServerPlayer) player);
                }
            }
        });



    }



    public abstract void mainAbility(Level level, Player player);
    public abstract void altAbility(Level level, Player player);




}
