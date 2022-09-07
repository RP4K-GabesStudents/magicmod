package epicmagicmod.magicmod.effect;

import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class DeathAuraEffect extends MobEffect {
    protected DeathAuraEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {


                switch(pAmplifier){

                    case 0:
                        break;


                    case 1:
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 5, 1), pLivingEntity);
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
