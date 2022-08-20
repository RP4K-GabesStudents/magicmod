package epicmagicmod.magicmod.networking.packet;

import epicmagicmod.magicmod.client.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaDataSyncS2CPacket {

    private final int mana;

    public ManaDataSyncS2CPacket(int mana){
        this.mana = mana;
    }

    public ManaDataSyncS2CPacket(FriendlyByteBuf buffer){
        this.mana = buffer.readInt();
    }

    public void toByte(FriendlyByteBuf buffer){
        buffer.writeInt(mana);
    }
    public boolean handle(Supplier<NetworkEvent.Context>supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()-> ClientManaData.setPlayerMana(mana));
        return true;
    }

}
