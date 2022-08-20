package epicmagicmod.magicmod.networking;

import epicmagicmod.magicmod.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import epicmagicmod.magicmod.main;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {

    private static SimpleChannel Instance;
    private static int packetID = 0;

    private static int ID(){

        return packetID++;

    }

    public static void register(){

        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(main.MODID, "messages")).
                networkProtocolVersion(()->"1.0").
                clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();

        Instance = net;
        net.messageBuilder(ManaDataSyncS2CPacket.class,ID(), NetworkDirection.PLAY_TO_CLIENT).decoder(ManaDataSyncS2CPacket::new).encoder(ManaDataSyncS2CPacket::toByte).consumerMainThread(ManaDataSyncS2CPacket::handle).add();
    }

    public static <MSG> void sendToServer(MSG message){

        Instance.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){

        Instance.send(PacketDistributor.PLAYER.with(()->player),message);

    }
}
