package epicmagicmod.magicmod.effect;

import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ManaModifyEffect extends MobEffect {
    public ManaModifyEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {

            pLivingEntity.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(playerMana -> {

                switch(pAmplifier){

                    case 1:
                        playerMana.augmentMana(2000, (ServerPlayer) pLivingEntity);
                        break;


                    case 2:
                        playerMana.augmentMana(5000, (ServerPlayer) pLivingEntity);
                        break;


                }




            });

        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}