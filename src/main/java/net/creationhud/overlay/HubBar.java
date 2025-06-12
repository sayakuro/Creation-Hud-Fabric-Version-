package net.creationhud.overlay;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class HubBar {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final Identifier Normal_Heart = new Identifier("creationhud", "textures/gui/bars/games_ui/heart/normal_heart.png");
    private static final Identifier Gold_Heart = new Identifier("creationhud", "textures/gui/bars/games_ui/heart/gold_heart.png");
    private static final Identifier Heart_Wither = new Identifier("creationhud", "textures/gui/bars/games_ui/heart/wither_heart.png");
    private static final Identifier Heart_Poision = new Identifier("creationhud", "textures/gui/bars/games_ui/heart/poision_heart.png");
    private static final Identifier Heart_Frozen = new Identifier("creationhud", "textures/gui/bars/games_ui/heart/frozen_heart.png");

    // Add mount-specific textures (you might want to create custom textures for these)
    private static final Identifier Mount_Line = new Identifier("creationhud", "textures/gui/bars/games_ui/texture_line.png");
    private static final Identifier Mount_Fill = new Identifier("creationhud", "textures/gui/bars/games_ui/texture_health.png");

    private static final Identifier Fill_Line = new Identifier("creationhud", "textures/gui/bars/games_ui/texture_line.png");
    private static final Identifier Fill_Hp = new Identifier("creationhud", "textures/gui/bars/games_ui/texture_health.png");
    private static final Identifier Fill_Gold_Hp = new Identifier("creationhud", "textures/gui/bars/games_ui/texture_gold-health.png");


    public void Renderhub(DrawContext context, float tickDelta) {
        if (mc.cameraEntity instanceof PlayerEntity player
                && !mc.options.hudHidden
                && mc.interactionManager != null && mc.interactionManager.hasStatusBars()) {

            float health = player.getHealth();
            float maxHealth = player.getMaxHealth();
            float healthProportion = health / maxHealth;
            float absorptionAmount = player.getAbsorptionAmount();
            if (healthProportion > 1) healthProportion = 1F;

            int screenWidth = mc.getWindow().getScaledWidth();
            int screenHeight = mc.getWindow().getScaledHeight();

            int healthBarWidth = 72;
            int healthBarHeight = 9;

            int healthBarX = (screenWidth / 2) - 98;
            int healthBarY = screenHeight - 39;
            // วาดแถบเลือด
            context.drawTexture(Fill_Line,
                    healthBarX + 7, healthBarY,
                    0, 0,
                    healthBarWidth, healthBarHeight,
                    healthBarWidth, healthBarHeight);

            context.drawTexture(Fill_Hp,
                    healthBarX + 7, healthBarY,
                    0, 0,
                    (int) (healthBarWidth * healthProportion), healthBarHeight,
                    healthBarWidth, healthBarHeight);

            if (absorptionAmount > 0) {
                // Calculate absorption proportion (typically 1 heart = 2 health points)
                // Maximum absorption in vanilla is 10 hearts (20 health points)
                // We'll scale it to show the correct proportion based on absorption amount
                float absorptionProportion = absorptionAmount / 20.0F; // Max 20 absorption points
                if (absorptionProportion > 1) absorptionProportion = 1F;

                // Calculate final width of gold bar
                int goldWidth = (int)(healthBarWidth * absorptionProportion);

                // Draw the gold health part - centered in the health bar
                context.drawTexture(Fill_Gold_Hp,
                        healthBarX + 7, healthBarY,
                        0, 0,
                        goldWidth, healthBarHeight,
                        healthBarWidth, healthBarHeight);
            }

            TextRenderer textRenderer = mc.textRenderer;

            // คำนวณและแสดงค่าเลือดรวม
            float healthText = health + absorptionAmount;
            String Texthealth = (healthText % 1 == 0) ? String.format("%.0f", healthText) : String.format("%.1f", healthText);

            int textWidth = textRenderer.getWidth(Texthealth);

            float scale = 0.65f;
            float scaledTextWidth = textWidth * scale;
            float scaledFontHeight = textRenderer.fontHeight * scale;

            float textX = healthBarX + (healthBarWidth / 2f) - (scaledTextWidth / 2f);
            float textY = healthBarY + (healthBarHeight / 2f) - (scaledFontHeight / 2f);

            context.getMatrices().push();                            // เริ่ม push matrix
            context.getMatrices().translate(textX, textY, 0);        // ย้ายไปตำแหน่งที่จะวาด
            context.getMatrices().scale(scale, scale, 1.0f);         // ย่อขนาด
            context.drawText(textRenderer, Texthealth, 0, 0, 0xFFFFFF, true);
            context.getMatrices().pop();                             // คืนค่า matrix

            //Heart_color
            Identifier Heart_Type;
            if (player.hasStatusEffect(StatusEffects.WITHER)) {
                Heart_Type = Heart_Wither;
            } else if (player.hasStatusEffect(StatusEffects.POISON)) {
                Heart_Type = Heart_Poision;
            } else if (player.isFrozen()) {
                Heart_Type = Heart_Frozen;
            } else if (absorptionAmount > 0) {
                Heart_Type = Gold_Heart;
            } else {
                Heart_Type = Normal_Heart;
            }
            context.drawTexture(Heart_Type,
                    healthBarX, healthBarY,
                    0, 0,
                    healthBarWidth + 7, healthBarHeight,
                    healthBarWidth + 7, healthBarHeight);

            // Render mount health bar if the player has a vehicle
            renderMountHealthBar(context, player);
        }
    }

    // Add the new Mount Health Bar method
    private void renderMountHealthBar(DrawContext context, PlayerEntity player) {
        if (player.hasVehicle() && player.getVehicle() instanceof LivingEntity mount) {
            float mountHealth = mount.getHealth();
            float mountMaxHealth = mount.getMaxHealth();
            float mountHealthProportion = mountHealth / mountMaxHealth;
            if (mountHealthProportion > 1) mountHealthProportion = 1F;

            int screenWidth = mc.getWindow().getScaledWidth();
            int screenHeight = mc.getWindow().getScaledHeight();

            int mountBarWidth = 79;
            int mountBarHeight = 9;

            // Position the mount bar above the health bar
            int mountBarX = (screenWidth / 2) + 12; // Opposite side from health bar
            int mountBarY = screenHeight - 39; // Same height as health bar

            // Draw mount bar background
            context.drawTexture(Mount_Line,
                    mountBarX, mountBarY,
                    0, 0,
                    mountBarWidth, mountBarHeight,
                    mountBarWidth, mountBarHeight);

            // Draw mount health fill
            context.drawTexture(Mount_Fill,
                    mountBarX, mountBarY,
                    0, 0,
                    (int) (mountBarWidth * mountHealthProportion), mountBarHeight,
                    mountBarWidth, mountBarHeight);

            // Draw mount health text
            TextRenderer textRenderer = mc.textRenderer;
            String mountHealthText = (mountHealth % 1 == 0) ? String.format("%.0f", mountHealth) : String.format("%.1f", mountHealth);
            int textWidth = textRenderer.getWidth(mountHealthText);

            float scale = 0.65f;
            float scaledTextWidth = textWidth * scale;
            float scaledFontHeight = textRenderer.fontHeight * scale;

            float textX = mountBarX + (mountBarWidth / 2f) - (scaledTextWidth / 2f);
            float textY = mountBarY + (mountBarHeight / 2f) - (scaledFontHeight / 2f);

            context.getMatrices().push();
            context.getMatrices().translate(textX, textY, 0);
            context.getMatrices().scale(scale, scale, 1.0f);
            context.drawText(textRenderer, mountHealthText, 0, 0, 0xFFFFFF, true);
            context.getMatrices().pop();
        }
    }
}