package epicmagicmod.magicmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import epicmagicmod.magicmod.main;
import net.minecraft.world.entity.player.Inventory;

public class ManaExtractionScreen extends AbstractContainerScreen<ManaExtractionMenu>{


    private static final ResourceLocation TEXTURE = new ResourceLocation(main.MODID, "textures/gui/mana_extractor_gui.png");

    public ManaExtractionScreen(ManaExtractionMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width-imageWidth)/2;
        int y = (height-imageHeight)/2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        renderProgresdsArrow(pPoseStack, x, y);

    }

    private void renderProgresdsArrow(PoseStack pPoseStack, int x, int y) {
        if(menu.isCrafting())
        {
            blit(pPoseStack, x + 105, y + 33, 176, 0, 8,menu.getScaledProgress());
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

}
