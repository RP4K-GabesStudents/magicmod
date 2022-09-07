package epicmagicmod.magicmod.effect;

import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class BlastPunchEffect extends MobEffect {
    protected BlastPunchEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }



    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity wielder){

        // Gets a vector of length 1 in the direction the player holding this item is looking
        Vec3 look = wielder.getLookAngle().normalize();

        // This value changes the amount of kb
        double knockback = 5.5;

        // Adds velocity to the target
        target.setDeltaMovement(look.x*knockback, look.y*knockback, look.z*knockback);

        return true;

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
