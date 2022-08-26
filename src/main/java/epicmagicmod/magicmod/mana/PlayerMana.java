package epicmagicmod.magicmod.mana;

import epicmagicmod.magicmod.networking.ModMessages;
import epicmagicmod.magicmod.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

public class PlayerMana {

    private int mana;
    private int manaPerTick = 1;

    public static final int MIN_MANA = 0;
    public static final int DEFAULT_MAX_MANA = 100000;
    public static int MAX_MANA = DEFAULT_MAX_MANA;




    public int getManaPerTick(){
        return manaPerTick;
    }
    public int getMana(){

        return mana;

    }

    public void setMana(int val){

        mana = val;

    }

    public void setMana(PlayerMana val){

        mana = val.mana;

    }

    public void augmentMana(int val, ServerPlayer player){

        mana += val;

        if (mana <= MIN_MANA){
            mana = MIN_MANA;
        }

        if (mana >= MAX_MANA){
            mana = MAX_MANA;
        }

        ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana), player);

    }

    public void saveNBTData(CompoundTag nbt){

        nbt.putInt("mana", mana);

    }

    public void loadNBTData(CompoundTag nbt){

        mana = nbt.getInt("mana");

    }




}
