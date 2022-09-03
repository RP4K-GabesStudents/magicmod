package epicmagicmod.magicmod.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MyFireball extends LargeFireball {

    private final int explosionPower;
    private final boolean breakBlocks;

    public MyFireball(Level pLevel, LivingEntity pShooter, Vec3 offset, int pExplosionPower, boolean breakBlocks) {
        super(pLevel, pShooter, offset.x, offset.y, offset.z, pExplosionPower);
        this.breakBlocks = breakBlocks;
        explosionPower = pExplosionPower;
    }

    @Override
    protected void onHit(HitResult pResult) {
        //super.onHit(pResult);
        if (!this.level.isClientSide) {

            List<Entity> ent = this.level.getEntities(this, new AABB(pResult.getLocation().x -1, pResult.getLocation().y -1,pResult.getLocation().z -1,pResult.getLocation().x +1,pResult.getLocation().y +1,pResult.getLocation().z +1));

            if(ent.size() == 0)
            {
                this.level.explode(null, this.getX(), this.getY(), this.getZ(), (float) explosionPower, breakBlocks, breakBlocks ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
                this.discard();
                return;
            }

            for(Entity e : ent) {

                if(!(e instanceof MyFireball)) {
                    this.level.explode(null, this.getX(), this.getY(), this.getZ(), (float) explosionPower, breakBlocks, breakBlocks ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
                    this.discard();
                    return;
                }
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        //super.onHitEntity(pResult);
        if (!this.level.isClientSide && !(pResult.getEntity() instanceof MyFireball)) {
            Entity entity = pResult.getEntity();
            Entity entity1 = this.getOwner();
            if(breakBlocks)
                entity.hurt(DamageSource.fireball(this, entity1), explosionPower);
            if (entity1 instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)entity1, entity);
            }

        }
    }

    @Override
    protected float getInertia() {
        return 1f;
    }
}
