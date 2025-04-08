package net.fatelesshub.overlay;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class HubBar {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final Identifier Normal_Heart = new Identifier("fatelesshub", "textures/gui/bars/fateless_ui/heart/normal_heart.png");
    private static final Identifier Gold_Heart = new Identifier("fatelesshub", "textures/gui/bars/fateless_ui/heart/gold_heart.png");
    private static final Identifier Heart_Wither = new Identifier("fatelesshub", "textures/gui/bars/fateless_ui/heart/wither_heart.png");
    private static final Identifier Heart_Poision = new Identifier("fatelesshub", "textures/gui/bars/fateless_ui/heart/poision_heart.png");
    private static final Identifier Heart_Frozen = new Identifier("fatelesshub", "textures/gui/bars/fateless_ui/heart/frozen_heart.png");


    private static final Identifier Fill_Line = new Identifier("fatelesshub", "textures/gui/bars/fateless_ui/texture_line.png");
    private static final Identifier Fill_Hp = new Identifier("fatelesshub", "textures/gui/bars/fateless_ui/texture_health.png");

    public void Renderhub(DrawContext context, float tickDelta) {
        if (mc.cameraEntity instanceof PlayerEntity player
                && !mc.options.hudHidden
                && mc.interactionManager != null && mc.interactionManager.hasStatusBars()) {

            float health = player.getHealth();
            float maxHealth = player.getMaxHealth();
            float healthProportion = health / maxHealth;
            if (healthProportion > 1) healthProportion = 1F;

            int screenWidth = mc.getWindow().getScaledWidth();
            int screenHeight = mc.getWindow().getScaledHeight();

            int healthBarWidth = 79;
            int healthBarHeight = 9;

            int healthBarX = (screenWidth / 2) - 91;
            int healthBarY = screenHeight - 39;
            context.drawTexture(Fill_Line,
                    healthBarX, healthBarY,
                    0, 0,
                    healthBarWidth, healthBarHeight,
                    healthBarWidth, healthBarHeight);

// วาดแถบเลือด
            context.drawTexture(Fill_Hp,
                    healthBarX, healthBarY,
                    0, 0,
                    (int) (healthBarWidth * healthProportion), healthBarHeight,
                    healthBarWidth, healthBarHeight);

            TextRenderer textRenderer = mc.textRenderer;
            String healthText = String.format("%.0f", health);
            int textWidth = textRenderer.getWidth(healthText);

            float scale = 0.65f;
            float scaledTextWidth = textWidth * scale;
            float scaledFontHeight = textRenderer.fontHeight * scale;

            float textX = healthBarX + (healthBarWidth / 2f) - (scaledTextWidth / 2f);
            float textY = healthBarY + (healthBarHeight / 2f) - (scaledFontHeight / 2f);

            context.getMatrices().push();                            // เริ่ม push matrix
            context.getMatrices().translate(textX, textY, 0);        // ย้ายไปตำแหน่งที่จะวาด
            context.getMatrices().scale(scale, scale, 1.0f);         // ย่อขนาด
            context.drawText(textRenderer, healthText, 0, 0, 0xFFFFFF, true);
            context.getMatrices().pop();                             // คืนค่า matrix

            //Heart_color
            Identifier Heart_Type;
            if (player.hasStatusEffect(StatusEffects.WITHER)) {
                Heart_Type = Heart_Wither;
            } else if (player.hasStatusEffect(StatusEffects.POISON)) {
                Heart_Type = Heart_Poision;
            } else if (player.hasStatusEffect(StatusEffects.ABSORPTION)) {
                Heart_Type = Gold_Heart;
            } else if (player.isFrozen()) {
                Heart_Type = Heart_Frozen;
            } else {
                Heart_Type = Normal_Heart;
            }
            context.drawTexture(Heart_Type,
                    healthBarX, healthBarY,
                    0, 0,
                    healthBarWidth, healthBarHeight,
                    healthBarWidth, healthBarHeight);
        }
    }
}