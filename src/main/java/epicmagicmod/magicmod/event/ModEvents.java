package epicmagicmod.magicmod.event;

import epicmagicmod.magicmod.block.ManaLiquidBlock;
import epicmagicmod.magicmod.block.ModBlocks;
import epicmagicmod.magicmod.client.ManaHudOverlay;
import epicmagicmod.magicmod.items.armor.ModArmorMaterials;
import epicmagicmod.magicmod.mana.PlayerMana;
import epicmagicmod.magicmod.mana.PlayerManaProvider;
import epicmagicmod.magicmod.networking.ModMessages;
import epicmagicmod.magicmod.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import epicmagicmod.magicmod.main;

@Mod.EventBusSubscriber(modid = main.MODID)
public class ModEvents {

    private static int deathMana;

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity>event){

        if (event.getObject() instanceof Player p) {

            if (!p.getCapability(PlayerManaProvider.PLAYER_MANA).isPresent()){

                event.addCapability(new ResourceLocation(main.MODID, "properties"), new PlayerManaProvider());

            }

        }
    }



    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event){

        if(event.isWasDeath()){

            event.getEntity().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    mana.setMana((int)(deathMana * 0.25f));
                    mana.augmentMana((int)(deathMana * 0.25f), (ServerPlayer)event.getEntity());
            });
            }

        }


   @SubscribeEvent
   public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){

        event.register(PlayerMana.class);

   }

   @SubscribeEvent
   public static void onArmorEvent(LivingEquipmentChangeEvent event){

        if (event.getEntity() instanceof Player player && event.getSlot().getIndex() != 0){

            event.getEntity().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->{

                if (event.getTo().getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() instanceof ModArmorMaterials mat){

                    mana.MAX_MANA += mat.MANA_CAP_INCREASE [armorItem.getSlot().getIndex()];
                    mana.augmentMana(0, (ServerPlayer) player);
                    player.sendSystemMessage(Component.literal("mana" + mana.MAX_MANA));
                }
                if (event.getFrom().getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() instanceof ModArmorMaterials mat){

                    mana.MAX_MANA -= mat.MANA_CAP_INCREASE [armorItem.getSlot().getIndex()];
                    mana.augmentMana(0, (ServerPlayer) player);
                    player.sendSystemMessage(Component.literal("mana" + mana.MAX_MANA));

                }



            });

        }


   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event){

       if (event.getEntity() instanceof Player player){

           player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->{

               deathMana = mana.getMana();

           });

       }
   }



   @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){

        if (event.side == LogicalSide.SERVER && event.player.isAlive()){

            event.player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {

                if (mana.getMana() < mana.MAX_MANA){
                    mana.augmentMana(mana.getManaPerTick(), (ServerPlayer)event.player);


                    ManaHudOverlay.SetOriginalColor();
                }

                if(event.player.isAffectedByFluids() && (event.player.getBlockStateOn().getBlock() instanceof ManaLiquidBlock || event.player.getFeetBlockState().getBlock() instanceof ManaLiquidBlock))
                {
                    ManaLiquidBlock mlb;
                    if(event.player.getBlockStateOn().getBlock() instanceof ManaLiquidBlock a)
                        mlb = a;
                    else
                        mlb = (ManaLiquidBlock)event.player.getFeetBlockState().getBlock();
                    for (MobEffectInstance me: mlb.EFFECTS)
                    {
                        event.player.addEffect(new MobEffectInstance(me.getEffect(), 0, me.getAmplifier()), event.player);
                    }
                }
            });

        }


   }

   @SubscribeEvent
    public static void onPlayerJoinsWorld(EntityJoinLevelEvent event){

        if  (!event.getLevel().isClientSide() && event.getEntity()instanceof ServerPlayer player){

            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->{

                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()),player);

            });

       }
   }

    @SubscribeEvent
    public static void setupBlockColors(RegisterColorHandlersEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        colors.register((state, level, pos, tint) -> tint == 1 ? (level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.get(0.5, 1)) : -1, ModBlocks.GRASS.get());
    }



}
