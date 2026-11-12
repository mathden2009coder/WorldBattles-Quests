package com.worldbattles.quests.client.gui;

import com.worldbattles.quests.network.ModNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class QuestScreen extends Screen {
    private static final int PANEL_WIDTH = 330;
    private static final int PANEL_HEIGHT = 246;
    private final String npcId;
    private final String npcName;
    private final String questTitle;
    private final String description;
    private final int progress;
    private final int targetCount;
    private final long nextReward;
    private final boolean deliveryQuest;
    private final boolean finished;
    private final String story;
    private final long earnedReward;
    private int panelLeft;
    private int panelTop;

    public QuestScreen(String npcId, String npcName, String questTitle, String description,
                       int progress, int targetCount, long reward, boolean deliveryQuest,
                       boolean finished, String story, long earnedReward) {
        super(Component.literal(npcName + " — Quêtes"));
        this.npcId = npcId;
        this.npcName = npcName;
        this.questTitle = questTitle;
        this.description = description;
        this.progress = Math.max(0, progress);
        this.targetCount = Math.max(0, targetCount);
        this.nextReward = Math.max(0, reward);
        this.deliveryQuest = deliveryQuest;
        this.finished = finished;
        this.story = story == null ? "" : story;
        this.earnedReward = Math.max(0, earnedReward);
    }

    private boolean hunterTheme() { return "hunter".equals(npcId); }
    private boolean mayllineTheme() { return "maylline".equals(npcId); }

    @Override
    protected void init() {
        panelLeft = (width - PANEL_WIDTH) / 2;
        panelTop = (height - PANEL_HEIGHT) / 2;
        int buttonY = panelTop + 216;
        if (!finished && deliveryQuest && progress < targetCount) {
            String label = mayllineTheme() ? "Déposer les trouvailles" : "Apporter les objets";
            addRenderableWidget(Button.builder(Component.literal(label),
                button -> ModNetworking.sendQuestAction("donate", npcId))
                .bounds(panelLeft + 14, buttonY, 164, 20).build());
        }
        if (!finished && progress >= targetCount && targetCount > 0) {
            String label = mayllineTheme() ? "Valider le chapitre" : "Valider la quête";
            addRenderableWidget(Button.builder(Component.literal(label),
                button -> ModNetworking.sendQuestAction("claim", npcId))
                .bounds(panelLeft + 14, buttonY, 164, 20).build());
        }
        addRenderableWidget(Button.builder(Component.literal("Fermer"), button -> onClose())
            .bounds(panelLeft + PANEL_WIDTH - 78, buttonY, 64, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        panelLeft = (width - PANEL_WIDTH) / 2;
        panelTop = (height - PANEL_HEIGHT) / 2;
        int right = panelLeft + PANEL_WIDTH;
        int bottom = panelTop + PANEL_HEIGHT;
        boolean hunter = hunterTheme();
        boolean maylline = mayllineTheme();
        boolean jarvis = "blacksmith".equals(npcId);

        // Three distinct but related palettes: forge, woodland, archive paper.
        int border = maylline ? 0xFF2B2119 : hunter ? 0xFF101A12 : 0xFF0B0C10;
        int panel = maylline ? 0xFFB99F79 : hunter ? 0xFF202A20 : 0xFF28231D;
        int header = maylline ? 0xFF477D78 : hunter ? 0xFF365A38 : 0xFF75451F;
        int content = maylline ? 0xFFF0E5CD : hunter ? 0xFF1B241C : 0xFF1D1B18;
        int accent = maylline ? 0xFF28736D : hunter ? 0xFF8ED66F : 0xFFFFC85A;
        int progressColor = maylline ? 0xFF61B8AC : hunter ? 0xFF65B96A : 0xFFE08B3B;
        int bodyText = maylline ? 0xFF453727 : 0xFFE8E2D8;
        int lorePanel = maylline ? 0xFFE5D6B8 : hunter ? 0xFF263529 : jarvis ? 0xFF30251B : content;
        int loreInk = maylline ? 0xFF453727 : 0xFFE8E2D8;

        graphics.fill(panelLeft - 2, panelTop - 2, right + 2, bottom + 2, border);
        graphics.fill(panelLeft, panelTop, right, bottom, panel);
        graphics.fill(panelLeft + 3, panelTop + 3, right - 3, panelTop + 25, header);
        graphics.fill(panelLeft + 3, panelTop + 26, right - 3, bottom - 3, content);
        if (maylline) {
            graphics.fill(panelLeft + 8, panelTop + 30, panelLeft + 9, bottom - 8, 0xFFD4C3A1);
            graphics.fill(right - 9, panelTop + 30, right - 8, bottom - 8, 0xFFD4C3A1);
        }

        ChatFormatting nameColor = maylline ? ChatFormatting.DARK_AQUA
            : hunter ? ChatFormatting.GREEN : ChatFormatting.GOLD;
        ChatFormatting titleColor = maylline ? ChatFormatting.DARK_AQUA
            : hunter ? ChatFormatting.GREEN : ChatFormatting.YELLOW;

        graphics.drawCenteredString(font, Component.literal(npcName).withStyle(
            nameColor, ChatFormatting.BOLD), width / 2, panelTop + 8, 0xFFFFFF);
        graphics.drawString(font, Component.literal(questTitle).withStyle(
            titleColor, ChatFormatting.BOLD), panelLeft + 13, panelTop + 35,
            maylline ? 0xFF28736D : 0xFFFFFF);

        int descY = panelTop + 50;
        for (var line : font.split(Component.literal(description), PANEL_WIDTH - 28)) {
            graphics.drawString(font, line, panelLeft + 13, descY, bodyText);
            descY += 10;
            if (descY > panelTop + 73) break;
        }

        // The story panel is shown at every interaction; after claiming it contains the new chapter.
        int loreTop = panelTop + 78;
        int loreBottom = panelTop + 146;
        graphics.fill(panelLeft + 10, loreTop, right - 10, loreBottom, lorePanel);
        graphics.fill(panelLeft + 10, loreTop, right - 10, loreTop + 1, accent);
        graphics.drawString(font, Component.literal(maylline ? "ARCHIVES DES FREELANDS"
            : "CHRONIQUES DES FREELANDS").withStyle(ChatFormatting.BOLD),
            panelLeft + 16, loreTop + 6, accent);
        int storyY = loreTop + 20;
        for (var line : font.split(Component.literal(story), PANEL_WIDTH - 34)) {
            graphics.drawString(font, line, panelLeft + 16, storyY, loreInk);
            storyY += 10;
            if (storyY > loreBottom - 10) break;
        }

        if (finished) {
            graphics.drawCenteredString(font, Component.literal("Tous les chapitres sont terminés !")
                .withStyle(maylline ? ChatFormatting.DARK_AQUA : ChatFormatting.GREEN),
                width / 2, panelTop + 157, maylline ? 0xFF28736D : 0xFFFFFF);
        } else {
            int safeTarget = Math.max(1, targetCount);
            int shown = Math.min(progress, safeTarget);
            graphics.drawString(font, Component.literal("Progression : " + shown + " / " + targetCount),
                panelLeft + 14, panelTop + 157, bodyText);
            int barX = panelLeft + 14;
            int barY = panelTop + 171;
            int barWidth = PANEL_WIDTH - 28;
            graphics.fill(barX, barY, barX + barWidth, barY + 7, maylline ? 0xFFB7A787 : 0xFF090909);
            int fillWidth = targetCount <= 0 ? 0 : (int) ((long) barWidth * shown / targetCount);
            if (fillWidth > 0) graphics.fill(barX + 1, barY + 1,
                barX + Math.min(barWidth - 1, fillWidth), barY + 6, progressColor);
            graphics.drawString(font, Component.literal("Prochaine récompense : " + nextReward + " $"),
                panelLeft + 14, panelTop + 184, accent);
        }

        if (earnedReward > 0) {
            graphics.drawString(font, Component.literal("Gain obtenu : +" + earnedReward + " $")
                .withStyle(ChatFormatting.BOLD), panelLeft + 14, panelTop + 198, 0xFFFFD76A);
        } else if (!finished) {
            graphics.drawString(font, Component.literal("Le montant sera ajouté à ton solde à la validation."),
                panelLeft + 14, panelTop + 198, bodyText);
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
