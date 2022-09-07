package epicmagicmod.magicmod.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class FireEffect extends MobEffect {
    protected FireEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }


    private void setFire(Level level, LivingEntity player){

        BlockPos playerPos = player.blockPosition();
        AABB area = new AABB(playerPos.getX() - 7, playerPos.getY() - 1, playerPos.getZ() - 7, playerPos.getX() + 7, playerPos.getY() + 2, playerPos.getZ() + 7);

        for (Entity e : level.getEntities(player, area)) {

            if (e instanceof LivingEntity living) {
                //   player.sendSystemMessage(Component.literal("we did a different thing hopefully to" + living.getName()));

                living.hurt(DamageSource.OUT_OF_WORLD, 0.80f);

            }

        }

    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {


                switch(pAmplifier){

                    case 0:
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 5, 1), pLivingEntity);
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5, 0), pLivingEntity);
                        break;


                    case 1:
                        setFire(pLivingEntity.level, pLivingEntity);
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 5, 1), pLivingEntity);
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5, 1), pLivingEntity);
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
