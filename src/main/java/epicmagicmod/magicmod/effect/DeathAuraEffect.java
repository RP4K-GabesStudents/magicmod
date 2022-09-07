package epicmagicmod.magicmod.effect;

import epicmagicmod.magicmod.items.wands.WandParent;
import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class DeathAuraEffect extends MobEffect{
    protected DeathAuraEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }


    private void doEffect(Level level, LivingEntity player){


        BlockPos playerPos = player.blockPosition();
        AABB area = new AABB(playerPos.getX() - 5, playerPos.getY() - 1, playerPos.getZ() - 5, playerPos.getX() + 5, playerPos.getY() + 2, playerPos.getZ() + 5);

        for (Entity e : level.getEntities(player, area)) {

            if (e instanceof LivingEntity living) {
                //   player.sendSystemMessage(Component.literal("we did a different thing hopefully to" + living.getName()));

                living.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 2), living);
                living.forceAddEffect(new MobEffectInstance(MobEffects.BLINDNESS, 2, 1), living);
            }

        }



    }

    private void doOtherEffect(Level level, LivingEntity player){


        BlockPos playerPos = player.blockPosition();
        AABB area = new AABB(playerPos.getX() - 5, playerPos.getY() - 1, playerPos.getZ() - 5, playerPos.getX() + 5, playerPos.getY() + 2, playerPos.getZ() + 5);

        for (Entity e : level.getEntities(player, area)) {

            if (e instanceof LivingEntity living) {
                //   player.sendSystemMessage(Component.literal("we did a different thing hopefully to" + living.getName()));

                living.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 4), living);
                living.forceAddEffect(new MobEffectInstance(MobEffects.BLINDNESS, 2, 2), living);
                living.forceAddEffect(new MobEffectInstance(MobEffects.CONFUSION, 2, 1), living);
                living.forceAddEffect(new MobEffectInstance(MobEffects.WITHER, 2, 2), living);
                living.forceAddEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 2, 1), living);
                living.forceAddEffect(new MobEffectInstance(MobEffects.WEAKNESS, 2, 3), living);
                living.forceAddEffect(new MobEffectInstance(MobEffects.UNLUCK, 2, 2), living);
                living.forceAddEffect(new MobEffectInstance(MobEffects.HUNGER, 2, 2), living);
                living.forceAddEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 2, 3), living);
            }

        }



    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {






        if (!pLivingEntity.level.isClientSide()) {


                switch(pAmplifier){

                    case 0:
                        doEffect(pLivingEntity.level, pLivingEntity);
                        break;


                    case 1:
                        doOtherEffect(pLivingEntity.level, pLivingEntity);
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 5, 1), pLivingEntity);
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 5, 1), pLivingEntity);
                        pLivingEntity.forceAddEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5, 3), pLivingEntity);
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
