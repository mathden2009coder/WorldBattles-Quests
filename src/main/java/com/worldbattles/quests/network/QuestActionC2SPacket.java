package com.worldbattles.quests.network;

import com.worldbattles.quests.quest.QuestService;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record QuestActionC2SPacket(String action, String npcId) {
    public static void encode(QuestActionC2SPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.action, 16);
        buf.writeUtf(msg.npcId, 64);
    }

    public static QuestActionC2SPacket decode(FriendlyByteBuf buf) {
        return new QuestActionC2SPacket(buf.readUtf(16), buf.readUtf(64));
    }

    public static void handle(QuestActionC2SPacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player == null) return;
            if ("donate".equals(msg.action)) {
                QuestService.donate(player, msg.npcId);
            } else if ("claim".equals(msg.action)) {
                QuestService.claim(player, msg.npcId);
            }
            QuestService.open(player, msg.npcId);
        });
        context.get().setPacketHandled(true);
    }
}