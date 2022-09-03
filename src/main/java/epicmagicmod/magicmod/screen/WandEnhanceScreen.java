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


public class WandEnhanceScreen extends AbstractContainerScreen<ManaExtractionMenu>{


    private static final ResourceLocation TEXTURE = new ResourceLocation(main.MODID, "textures/gui/mana_extractor_gui.png");

    public WandEnhanceScreen(ManaExtractionMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        imageWidth = 176; // 176 by def
        imageHeight = 166; // 166 by def
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width-imageWidth)/2;
        int y = (height-imageHeight)/2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        renderProgressArrow(pPoseStack, x, y);


        RenderSystem.setShaderColor(
                FastColor.ARGB32.red(menu.getFluidColor()) / 255f,
                FastColor.ARGB32.green(menu.getFluidColor()) /255f,
                FastColor.ARGB32.blue(menu.getFluidColor()) / 255f,
                FastColor.ARGB32.alpha(menu.getFluidColor()) / 255f);
        renderFluidBar(pPoseStack, x, y);

    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y) {
        if(menu.isCrafting())
        {
            blit(pPoseStack, x + 49, y + 19, 176, 0, 8,menu.getScaledArrowProgress());
            blit(pPoseStack, x + 78, y + 19, 176, 0, 8,menu.getScaledArrowProgress());
        }
    }

    private void renderFluidBar(PoseStack pPoseStack, int x, int y) {
        Logger.getAnonymousLogger().info("TEST: " + (18+(64-menu.getScaledFluidProgress())));

        //When PV is end - cur it goes up... (13+64-scale)
        blit(pPoseStack, x + 156, y + 77 - menu.getScaledFluidProgress(), 176, 18, 8, menu.getScaledFluidProgress());
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

}
