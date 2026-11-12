package com.worldbattles.quests.network;

import com.worldbattles.quests.WorldBattlesQuests;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class ModNetworking {
    // Protocol 2 adds the story fragment and the amount actually paid by a claimed chapter.
    private static final String PROTOCOL = "2";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
        .named(new ResourceLocation(WorldBattlesQuests.MODID, "main"))
        .networkProtocolVersion(() -> PROTOCOL)
        .clientAcceptedVersions(PROTOCOL::equals)
        .serverAcceptedVersions(PROTOCOL::equals)
        .simpleChannel();

    private static boolean registered;

    public static void register() {
        if (registered) return;
        int id = 0;
        CHANNEL.registerMessage(id++, OpenQuestScreenS2CPacket.class,
            OpenQuestScreenS2CPacket::encode, OpenQuestScreenS2CPacket::decode,
            OpenQuestScreenS2CPacket::handle);
        CHANNEL.registerMessage(id++, QuestActionC2SPacket.class,
            QuestActionC2SPacket::encode, QuestActionC2SPacket::decode,
            QuestActionC2SPacket::handle);
        registered = true;
    }

    public static void openQuestScreen(ServerPlayer player, String npcId, String npcName,
                                       String title, String description, int progress,
                                       int targetCount, long reward, boolean deliveryQuest,
                                       boolean finished, String story, long earnedReward) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
            new OpenQuestScreenS2CPacket(npcId, npcName, title, description, progress,
                targetCount, reward, deliveryQuest, finished, story, earnedReward));
    }

    public static void sendQuestAction(String action, String npcId) {
        CHANNEL.sendToServer(new QuestActionC2SPacket(action, npcId));
    }

    private ModNetworking() {}
}
