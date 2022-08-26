package epicmagicmod.magicmod.items.wands;

import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.logging.Logger;


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

                    if(mainAbility(level, player)) {
                        player.sendSystemMessage(Component.literal("MAIN ATTACK"));
                        playerMana.augmentMana(-mainManaUsage, (ServerPlayer) player);
                    }
                    else
                    {
                        player.sendSystemMessage(Component.literal("MAIN ATTACK MISSED"));
                    }
                }
            }
            else
            {
                if (playerMana.getMana() >= mainManaUsage || player.isCreative()){

                    if(altAbility(level, player)) {
                        player.sendSystemMessage(Component.literal("ALT ATTACK"));

                        playerMana.augmentMana(-mainManaUsage, (ServerPlayer) player);
                    }
                    else{
                        player.sendSystemMessage(Component.literal("ALT ATTACK MISSED"));
                    }
                }
            }
        });
    }

    protected static LivingEntity GetLookAtTarget(Level level, Player player, double rayLength, boolean playerOnly)
    {
        //RAY END POINT - TO WHERE IT WILL TRAVEL TO
        Vec3 playerRotation = player.getViewVector(1f).normalize();
        Vec3 rayPath = playerRotation.scale(rayLength);

        Vec3 from = player.getEyePosition(0);
        Vec3 to = from.add(rayPath);

        //CREATE THE RAY
        ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
        //CAST THE RAY
        BlockHitResult rayHit = level.clip(rayCtx);
        int scalar = 2;
        int casts = (int)Math.ceil(rayHit.getLocation().distanceTo(from) /  scalar);

        Logger.getAnonymousLogger().info("Created #" + casts + "arrays");


        LivingEntity target = null;
        double distToTarget = 100000000;

        boolean miss = rayHit.getType() == HitResult.Type.MISS;
        Vec3 hitLoc = rayHit.getLocation();



        for(int i = 0; i < casts; i++)
        {
            Vec3 thisRay = from.add(playerRotation.scale((i+1) * scalar));
            AABB thisHitBox = new AABB(thisRay.x-scalar, thisRay.y-scalar, thisRay.z-scalar, thisRay.x+scalar, thisRay.y+scalar, thisRay.z+scalar);

            for (Entity e : level.getEntities(player, thisHitBox)) {
                if (e instanceof LivingEntity le) {
                    double pt;
                    if(!playerOnly || (playerOnly && le instanceof Player)) {
                        if (miss) {
                            pt = le.distanceToSqr(thisRay.x, thisRay.y, thisRay.z);
                            if (pt < distToTarget) {
                                distToTarget = pt;
                                target = le;
                            }
                        } else {
                            pt = le.distanceToSqr(hitLoc.x, hitLoc.y, hitLoc.z);
                            if (pt < distToTarget) {
                                distToTarget = pt;
                                target = le;
                            }
                        }
                    }
                }
            }
        }
        return target;
    }



    public abstract boolean mainAbility(Level level, Player player);
    public abstract boolean altAbility(Level level, Player player);




}
