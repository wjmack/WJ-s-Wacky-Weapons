package wjmack.wjs.weapons;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class WWWClient implements ClientModInitializer {
    private static final Identifier GLASS_OVERLAY = new Identifier("minecraft", "textures/misc/pumpkinblur.png");

    @Override
    public void onInitializeClient() {
        // In Game Hud Callback
        HudRenderCallback.EVENT.register(((matrixStack, tickDelta) -> {
            ItemStack helmetSlot = MinecraftClient.getInstance().player.getInventory().getArmorStack(3);
            if (helmetSlot.isOf(WJsWackyWeapons.SPACE_HELMET.asItem())) {
                renderOverlay(GLASS_OVERLAY, 0.5F);
            }
        }));
    }

    private void renderOverlay(Identifier texture, float opacity) {
        int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
        RenderSystem.setShaderTexture(0, texture);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(0.0D, scaledHeight, -90.0D).texture(0.0F, 1.0F).next();
        bufferBuilder.vertex(scaledWidth, scaledHeight, -90.0D).texture(1.0F, 1.0F).next();
        bufferBuilder.vertex(scaledWidth, 0.0D, -90.0D).texture(1.0F, 0.0F).next();
        bufferBuilder.vertex(0.0D, 0.0D, -90.0D).texture(0.0F, 0.0F).next();
        tessellator.draw();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}