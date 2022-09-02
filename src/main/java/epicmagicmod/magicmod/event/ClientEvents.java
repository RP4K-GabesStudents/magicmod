package epicmagicmod.magicmod.event;

import epicmagicmod.magicmod.block.EpicDirt;
import epicmagicmod.magicmod.client.ManaHudOverlay;
import epicmagicmod.magicmod.main;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


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

        /*
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            EpicDirt.registerRenderLayer();
        }*/

        @SubscribeEvent
        public static void blockColorLoad(RegisterColorHandlersEvent.Block event) {
            EpicDirt.blockColorLoad(event);
        }

    }






}
