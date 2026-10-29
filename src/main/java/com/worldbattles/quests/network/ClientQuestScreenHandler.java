package com.worldbattles.quests.network;

import com.worldbattles.quests.client.gui.QuestScreen;
import net.minecraft.client.Minecraft;

/**
 * Client-only entry point for opening the quest GUI.
 * This class is referenced through DistExecutor so dedicated servers never load it.
 */
public final class ClientQuestScreenHandler {
    public static void open(OpenQuestScreenS2CPacket msg) {
        Minecraft.getInstance().setScreen(new QuestScreen(msg.npcId(), msg.npcName(),
            msg.title(), msg.description(), msg.progress(), msg.targetCount(), msg.reward(),
            msg.deliveryQuest(), msg.finished()));
    }

    private ClientQuestScreenHandler() {}
}
