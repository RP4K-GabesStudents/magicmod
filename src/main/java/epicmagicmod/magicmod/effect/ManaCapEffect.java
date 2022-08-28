package epicmagicmod.magicmod.effect;

import epicmagicmod.magicmod.mana.PlayerMana;
import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class ManaCapEffect extends MobEffect {


    protected ManaCapEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }


    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {

            pLivingEntity.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(playerMana -> {

                switch(pAmplifier){

                    case 0:
                        PlayerMana.MAX_MANA += 25000;
                        break;


                    case 1:
                        PlayerMana.MAX_MANA += 60000;
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, 4), pLivingEntity);
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 5, 2), pLivingEntity);
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
