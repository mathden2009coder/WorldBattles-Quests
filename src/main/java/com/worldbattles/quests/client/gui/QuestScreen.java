package com.worldbattles.quests.client.gui;

import com.worldbattles.quests.network.ModNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class QuestScreen extends Screen {
    private static final int PANEL_WIDTH = 292;
    private static final int PANEL_HEIGHT = 178;
    private final String npcId;
    private final String npcName;
    private final String questTitle;
    private final String description;
    private final int progress;
    private final int targetCount;
    private final long reward;
    private final boolean deliveryQuest;
    private final boolean finished;
    private int panelLeft;
    private int panelTop;

    public QuestScreen(String npcId, String npcName, String questTitle, String description,
                       int progress, int targetCount, long reward, boolean deliveryQuest,
                       boolean finished) {
        super(Component.literal(npcName + " — Quêtes"));
        this.npcId = npcId;
        this.npcName = npcName;
        this.questTitle = questTitle;
        this.description = description;
        this.progress = Math.max(0, progress);
        this.targetCount = Math.max(0, targetCount);
        this.reward = Math.max(0, reward);
        this.deliveryQuest = deliveryQuest;
        this.finished = finished;
    }

    private boolean hunterTheme() { return "hunter".equals(npcId); }

    @Override
    protected void init() {
        panelLeft = (width - PANEL_WIDTH) / 2;
        panelTop = (height - PANEL_HEIGHT) / 2;
        int buttonY = panelTop + PANEL_HEIGHT - 31;
        if (!finished && deliveryQuest && progress < targetCount) {
            addRenderableWidget(Button.builder(Component.literal("Apporter les objets"),
                button -> ModNetworking.sendQuestAction("donate", npcId))
                .bounds(panelLeft + 16, buttonY, 156, 20).build());
        }
        if (!finished && progress >= targetCount && targetCount > 0) {
            addRenderableWidget(Button.builder(Component.literal("Réclamer la récompense"),
                button -> ModNetworking.sendQuestAction("claim", npcId))
                .bounds(panelLeft + 16, buttonY, 176, 20).build());
        }
        addRenderableWidget(Button.builder(Component.literal("Fermer"), button -> onClose())
            .bounds(panelLeft + PANEL_WIDTH - 76, buttonY, 60, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        panelLeft = (width - PANEL_WIDTH) / 2;
        panelTop = (height - PANEL_HEIGHT) / 2;
        int right = panelLeft + PANEL_WIDTH;
        int bottom = panelTop + PANEL_HEIGHT;
        boolean hunter = hunterTheme();

        int border = hunter ? 0xFF101A12 : 0xFF0B0C10;
        int panel = hunter ? 0xFF202A20 : 0xFF28231D;
        int header = hunter ? 0xFF365A38 : 0xFF513B25;
        int content = hunter ? 0xFF1B241C : 0xFF1D1B18;
        int accent = hunter ? 0xFF8ED66F : 0xFFFFC85A;
        int progressColor = hunter ? 0xFF65B96A : 0xFF61B55D;

        graphics.fill(panelLeft - 2, panelTop - 2, right + 2, bottom + 2, border);
        graphics.fill(panelLeft, panelTop, right, bottom, panel);
        graphics.fill(panelLeft + 3, panelTop + 3, right - 3, panelTop + 25, header);
        graphics.fill(panelLeft + 3, panelTop + 26, right - 3, bottom - 3, content);

        graphics.drawCenteredString(font, Component.literal(npcName).withStyle(
            hunter ? ChatFormatting.GREEN : ChatFormatting.GOLD, ChatFormatting.BOLD),
            width / 2, panelTop + 8, 0xFFFFFF);
        graphics.drawString(font, Component.literal(questTitle).withStyle(
            hunter ? ChatFormatting.GREEN : ChatFormatting.YELLOW, ChatFormatting.BOLD),
            panelLeft + 13, panelTop + 36, 0xFFFFFF);

        int descY = panelTop + 53;
        for (var line : font.split(Component.literal(description), PANEL_WIDTH - 28)) {
            graphics.drawString(font, line, panelLeft + 13, descY, 0xE8E2D8);
            descY += 11;
            if (descY > panelTop + 91) break;
        }

        if (finished) {
            graphics.drawCenteredString(font, Component.literal("Toutes les quêtes sont terminées !")
                .withStyle(ChatFormatting.GREEN), width / 2, panelTop + 105, 0xFFFFFF);
        } else {
            int safeTarget = Math.max(1, targetCount);
            int shown = Math.min(progress, safeTarget);
            graphics.drawString(font, Component.literal("Progression : " + shown + " / " + targetCount),
                panelLeft + 13, panelTop + 105, 0xFFFFFF);
            int barX = panelLeft + 13;
            int barY = panelTop + 120;
            int barWidth = PANEL_WIDTH - 26;
            graphics.fill(barX, barY, barX + barWidth, barY + 8, 0xFF090909);
            int fillWidth = targetCount <= 0 ? 0 : (int) ((long) barWidth * shown / targetCount);
            if (fillWidth > 0) graphics.fill(barX + 1, barY + 1,
                barX + Math.min(barWidth - 1, fillWidth), barY + 7, progressColor);
            graphics.drawString(font, Component.literal("Récompense : " + reward + " $"),
                panelLeft + 13, panelTop + 135, accent);
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
