package epicmagicmod.magicmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import epicmagicmod.magicmod.main;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Inventory;

import java.util.logging.Logger;


public class WandEnhanceScreen extends AbstractContainerScreen<WandEnhanceMenu>{


    private static final ResourceLocation TEXTURE = new ResourceLocation(main.MODID, "textures/gui/upgrade_altar_gui.png");

    public WandEnhanceScreen(WandEnhanceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        imageWidth = 176; // 176 by def
        imageHeight = 182; // 166 by def
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width-imageWidth)/2;
        int y = (height-imageHeight)/2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);


        if(!menu.isCrafting()) {
            for (int i = 0; i < 9; i++) // each inventory idx
            {
                switch (menu.getValidItem(i + 3)) {
                    case -1 -> RenderSystem.setShaderColor(0.54f, 0.0963f, 0.081f, 1f);
                    case 0 -> RenderSystem.setShaderColor(0f, 0f, 0f, 0.2f);
                    case 1 -> RenderSystem.setShaderColor(0.453f, 1f, 0.330f, 1f);
                }
                blit(pPoseStack, x + WandEnhanceMenu.LOCATIONS[i].getX() - 8, y + WandEnhanceMenu.LOCATIONS[i].getY(), imageWidth, 0, 32, 32);
            }
        }
        else
        {
            for (int i = 0; i < 9; i++) // each inventory idx
            {
                RenderSystem.setShaderColor(
                        FastColor.ARGB32.red(menu.getFluidColor()) / 255f,
                        FastColor.ARGB32.green(menu.getFluidColor()) /255f,
                        FastColor.ARGB32.blue(menu.getFluidColor()) / 255f,
                       FastColor.ARGB32.alpha(menu.getFluidColor()) / 255f);
                int prog = getMenu().getProgress();
                Logger.getAnonymousLogger().info(i + ": " + (71-prog) + "<" + (WandEnhanceMenu.LOCATIONS[i].getY() + 32) );
                if(71-prog < WandEnhanceMenu.LOCATIONS[i].getY() + 32) {
                    int sum =  (WandEnhanceMenu.LOCATIONS[i].getY() + 32) - (71-prog);
                    Logger.getAnonymousLogger().info(i + ": " + sum );
                    blit(pPoseStack, x + WandEnhanceMenu.LOCATIONS[i].getX() - 8, y + WandEnhanceMenu.LOCATIONS[i].getY(), imageWidth, 0, 32, sum);
                }
            }
            //blit(pPoseStack, x + WandEnhanceMenu.LOCATIONS[i].getX() - 8, y + WandEnhanceMenu.LOCATIONS[i].getY() - menu.getProgress(), imageWidth, 0, 32, 32-92+menu.getProgress());
            //Circular Fill
            blit(pPoseStack, x+32, y+14, 0, imageHeight, 118, menu.getProgress());
        }

        //renderProgressArrow(pPoseStack, x, y);




    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

}
