package epicmagicmod.magicmod.effect;

import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

                    case 0:
                        playerMana.augmentMana(2000, (ServerPlayer) pLivingEntity);
                        break;


                    case 1:
                        playerMana.augmentMana(5000, (ServerPlayer) pLivingEntity);
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5, 3), pLivingEntity);
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