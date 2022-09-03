package epicmagicmod.magicmod.items.wands;

import epicmagicmod.magicmod.block.ModBlocks;
import epicmagicmod.magicmod.block.ShardOreItem;
import epicmagicmod.magicmod.fluid.fluids.ManaFluid;
import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;


public abstract class WandParent extends Item {


    private int mainManaUsage;

    protected int getMainManaUsage(ItemStack is)
    {
        return (int)(mainManaUsage * (getLVL(is)+1) * costMult);
    }

    private int altManaUsage;

    protected int getAltManaUsage(ItemStack is)
    {
        return (int)(altManaUsage * (getLVL(is)+1) * costMult);
    }

    String name;
    protected int getLVL(ItemStack is){

        if(!is.hasTag())
            return 0;

        return is.getTag().getInt("testlvl");
    }

    private ShardOreItem.EOreType bound;
    private ShardOreItem item;

    public Item getShard()
    {
        if(item == null)
        {
            setOreItem();
        }
        return item.getShard();
    }

    public ManaFluid getFluid()
    {
        if(item == null)
        {
            setOreItem();
        }
        return item.getFluid();
    }

    private void setOreItem()
    {
        switch (bound) {
            case Blacite -> item = ModBlocks.BlaciteOreItem.get();
            case Granizous -> item = ModBlocks.GrazinousOreItem.get();
            case Mallumon -> item = ModBlocks.MallumonOreItem.get();
            case Torintrin -> item = ModBlocks.TorintrinOreItem.get();
        }
    }

    protected final float costMult;

    public WandParent(Properties properties, int mainManaUsage, int altManaUsage, String name, float costMult, ShardOreItem.EOreType bound) {
        super(properties);
        this.mainManaUsage = mainManaUsage;
        this.altManaUsage = altManaUsage;
        this.costMult = costMult;
        this.name = name;
        this.bound = bound;

    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        return switch (getLVL(pStack))
        {
            case 0 -> Rarity.COMMON;
            case 1 -> Rarity.UNCOMMON;
            case 2 -> Rarity.RARE;
            default -> Rarity.EPIC;
        };
    }

    private static ItemStack LevelUpWand(ItemStack is)
    {
        int lvl = 0;
        if(is.hasTag()) {
            Logger.getAnonymousLogger().info("BEFORE: " + is.getTag().getInt("testlvl"));

            lvl = is.getTag().getInt("testlvl");
        }
        CompoundTag nbtData = new CompoundTag();

        nbtData.putInt("testlvl", lvl+1);
        is.setTag(nbtData);

        Logger.getAnonymousLogger().info("AFTER: " + is.getTag().getInt("testlvl"));


        return is;
    }




    @Override
    public Component getName(ItemStack pStack) {

        return switch (getLVL(pStack))
                {
                    case 0 -> Component.literal(name);
                    case 1 -> Component.literal("Novice " + name);
                    case 2 -> Component.literal("Adept "+ name);
                    default -> Component.literal("Empowered " + name + " LV. " + (getLVL(pStack)-2));
                };
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return getLVL(pStack) >= 3;
    }

    // this is what all wands must do to shoot
    public final void activate(Level level, Player player){
        if(level.isClientSide()){
            return;
        }
        player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(playerMana -> {

            if(!player.isCrouching())
            {
                if (playerMana.getMana() >= getMainManaUsage(player.getItemInHand(InteractionHand.MAIN_HAND)) || player.isCreative()){

                    if(mainAbility(level, player)) {
                        player.sendSystemMessage(Component.literal("MAIN ATTACK"));
                        playerMana.augmentMana(-getMainManaUsage(player.getItemInHand(InteractionHand.MAIN_HAND)), (ServerPlayer) player);
                    }
                    else
                    {
                        player.sendSystemMessage(Component.literal("MAIN ATTACK MISSED"));
                    }
                }
            }
            else
            {
                if (playerMana.getMana() >= getAltManaUsage(player.getItemInHand(InteractionHand.MAIN_HAND)) || player.isCreative()){

                    if(altAbility(level, player)) {
                        player.sendSystemMessage(Component.literal("ALT ATTACK"));

                        playerMana.augmentMana(-getAltManaUsage(player.getItemInHand(InteractionHand.MAIN_HAND)), (ServerPlayer) player);
                        //player.setItemInHand(InteractionHand.MAIN_HAND, LevelUpWand(player.getItemInHand(InteractionHand.MAIN_HAND)));
                    }
                    else{
                        player.sendSystemMessage(Component.literal("ALT ATTACK MISSED"));
                    }
                }
            }
        });
    }

    protected LivingEntity getLookAtTarget(Level level, Player player, double rayLength, boolean playerOnly)
    {
        //RAY END POINT - TO WHERE IT WILL TRAVEL TO
        Vec3 playerRotation = player.getViewVector(1f).normalize();
        Vec3 rayPath = playerRotation.scale(rayLength * getLVL(player.getItemInHand(InteractionHand.MAIN_HAND)));

        Vec3 from = player.getEyePosition(0);
        Vec3 to = from.add(rayPath);

        //CREATE THE RAY
        ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
        //CAST THE RAY
        BlockHitResult rayHit = level.clip(rayCtx);
        int scalar = 2;
        int casts = (int)Math.ceil(rayHit.getLocation().distanceTo(from) /  scalar);


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

    protected List<Entity> getEntitiesInAOE(Level level, Player player, double rayLength, int radius)
    {
        return level.getEntities(player, getHitArea(level, player, rayLength, radius));
    }

    protected List<Player> getPlayersInAOE(Level level, Player player, double rayLength, int radius)
    {
        return level.getEntities(EntityType.PLAYER,  getHitArea(level, player, rayLength, radius), Predicate.isEqual(player).negate());
    }

    private AABB getHitArea(Level level, Player player, double rayLength, int radius)
    {
        //RAY END POINT - TO WHERE IT WILL TRAVEL TO
        Vec3 playerRotation = player.getViewVector(1f).normalize();
        Vec3 rayPath = playerRotation.scale(rayLength * this.getLVL(player.getItemInHand(InteractionHand.MAIN_HAND)));

        Vec3 from = player.getEyePosition(0);
        Vec3 to = from.add(rayPath);

        //CREATE THE RAY
        ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
        //CAST THE RAY
        BlockHitResult rayHit = level.clip(rayCtx);


        Vec3 hitLoc = rayHit.getLocation();


        if(rayHit.getType() == HitResult.Type.MISS) {

            int scalar = 1;
            int casts = (int) Math.ceil(rayHit.getLocation().distanceTo(from) / scalar);

            for (int i = 0; i < casts; i++) {
                Vec3 thisRay = from.add(playerRotation.scale((i + 1) * scalar));
                AABB thisHitBox = new AABB(thisRay.x - scalar, thisRay.y - scalar, thisRay.z - scalar, thisRay.x + scalar, thisRay.y + scalar, thisRay.z + scalar);

                for (Entity e : level.getEntities(player, thisHitBox)) {
                    if (e instanceof LivingEntity le) {
                        hitLoc = le.getPosition(0f);
                        casts = 0; // End both loops
                        break;
                    }
                }
            }
        }

        return new AABB(hitLoc.x-radius, hitLoc.y-radius, hitLoc.z-radius,hitLoc.x+radius, hitLoc.y+radius, hitLoc.z+radius);
    }


    protected boolean CanCast(Player player, LivingEntity target) {

        if(getLVL(player.getItemInHand(InteractionHand.MAIN_HAND)) == 1)
        {
            if (target instanceof Player)
            {
                return false;
            }
        }

        if(getLVL(player.getItemInHand(InteractionHand.MAIN_HAND)) <= 2)
        {
            if(target instanceof WitherBoss || target instanceof EnderDragon)
            {
                return false;
            }
        }
        return true;
    }



    public abstract boolean mainAbility(Level level, Player player);
    public abstract boolean altAbility(Level level, Player player);




}
