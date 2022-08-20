package epicmagicmod.magicmod.client;

public class ClientManaData {

    private static int playerMana;

    public static void setPlayerMana(int mana){
        playerMana = mana;
    }

    public static int getPlayerMana(){
        return playerMana;
    }

}
