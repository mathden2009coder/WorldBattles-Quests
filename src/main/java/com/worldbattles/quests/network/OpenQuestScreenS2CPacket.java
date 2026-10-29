package com.worldbattles.quests.network;

import com.worldbattles.quests.client.gui.QuestScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record OpenQuestScreenS2CPacket(String npcId, String npcName, String title,
                                       String description, int progress, int targetCount,
                                       long reward, boolean deliveryQuest, boolean finished) {
    public static void encode(OpenQuestScreenS2CPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.npcId, 64);
        buf.writeUtf(msg.npcName, 128);
        buf.writeUtf(msg.title, 256);
        buf.writeUtf(msg.description, 1024);
        buf.writeVarInt(msg.progress);
        buf.writeVarInt(msg.targetCount);
        buf.writeVarLong(msg.reward);
        buf.writeBoolean(msg.deliveryQuest);
        buf.writeBoolean(msg.finished);
    }

    public static OpenQuestScreenS2CPacket decode(FriendlyByteBuf buf) {
        return new OpenQuestScreenS2CPacket(buf.readUtf(64), buf.readUtf(128),
            buf.readUtf(256), buf.readUtf(1024), buf.readVarInt(), buf.readVarInt(),
            buf.readVarLong(), buf.readBoolean(), buf.readBoolean());
    }

    public static void handle(OpenQuestScreenS2CPacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
            Minecraft.getInstance().setScreen(new QuestScreen(msg.npcId, msg.npcName,
                msg.title, msg.description, msg.progress, msg.targetCount, msg.reward,
                msg.deliveryQuest, msg.finished))));
        context.get().setPacketHandled(true);
    }
}