package epicmagicmod.magicmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class SpeedyEffect extends MobEffect {


    protected SpeedyEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }


    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {


                switch(pAmplifier){

                    case 0:
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, 2), pLivingEntity);
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 5, 1), pLivingEntity);
                        break;


                    case 1:
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, 4), pLivingEntity);
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 5, 2), pLivingEntity);
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.REGENERATION, 5, 1), pLivingEntity);
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.JUMP, 5, 5), pLivingEntity);
                        break;

                }



        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }


}
