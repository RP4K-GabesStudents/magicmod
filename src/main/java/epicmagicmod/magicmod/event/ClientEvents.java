package epicmagicmod.magicmod.event;

import epicmagicmod.magicmod.client.ManaHudOverlay;
import epicmagicmod.magicmod.main;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class ClientEvents {

    @Mod.EventBusSubscriber(modid = main.MODID,  value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){

            //event.register(superAttack);


        }

        @SubscribeEvent
        public static void registerGUIOverlays(RegisterGuiOverlaysEvent event){

            event.registerAboveAll("mana", ManaHudOverlay.HUD_MANA);
            ManaHudOverlay.SetOriginalColor();

        }

    }






}
