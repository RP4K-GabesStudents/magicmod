package epicmagicmod.magicmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import epicmagicmod.magicmod.main;
import epicmagicmod.magicmod.mana.PlayerMana;
import epicmagicmod.magicmod.mana.PlayerManaProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaHudOverlay {

    private static final ResourceLocation MANA_BAR = new ResourceLocation(main.MODID, "textures/mana/bar.png");
    private static final ResourceLocation MANA_FILL = new ResourceLocation(main.MODID, "textures/mana/fill.png");

    public static float red;
    public static float green;
    public static float blue;

    private static final float originalRed = 0.0f;
    private static final float originalGreen = 0.321f;
    private static final float originalBlue = 1.0f;

    public static void SetOriginalColor(){

        red = originalRed;
        blue = originalBlue;
        green = originalGreen;
    }
    public static void ModifyColors(){

        float speed = 0.01f;
        if(red >= 1 && blue <= 0){
            green += speed;
        }

        if(green >= 1 && blue <= 0){
            red -= speed;
        }

        if(green >= 1 && red <= 0){
            blue += speed;
        }

        if(blue >=1 && red <= 0){
            green -= speed;
        }

        if(blue >= 1 && green <= 0){
            red += speed;
        }

        if(red >= 1 && green <= 0){
            blue -= speed;
        }

    }

    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {

        if (gui.getMinecraft().player.isCreative()){
            return;
        }
        gui.getMinecraft().player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {

            int x = screenWidth / 2;
            int y = screenHeight;

            RenderSystem.setShader(GameRenderer::getPositionColorTexShader);

            int maxBarWidth = 80;

            int fillWidth = (int) ((ClientManaData.getPlayerMana() * 1.0f / mana.MAX_MANA) * maxBarWidth);

            if(fillWidth == maxBarWidth)
                ModifyColors();

            gui.getMinecraft().player.sendSystemMessage(Component.literal("Val: " + ClientManaData.getPlayerMana() + " / " + PlayerMana.MAX_MANA + " -->" + ((ClientManaData.getPlayerMana() * 1f / PlayerMana.MAX_MANA) * 180f) + " - " + fillWidth).withStyle(ChatFormatting.AQUA));

            //drawing the filled bar
            RenderSystem.setShaderColor(red, green, blue, 1);

            RenderSystem.setShaderTexture(0, MANA_FILL);
            GuiComponent.blit(poseStack, x + 10, y - 48, 0, 0, fillWidth, 4, maxBarWidth, 4);


            //drawing the overlay
            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.setShaderTexture(0, MANA_BAR);
            GuiComponent.blit(poseStack, x + 10, y - 48, 0, 0, maxBarWidth, 4, maxBarWidth, 4);

        });


    });

}
