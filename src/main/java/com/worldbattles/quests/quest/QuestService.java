package com.worldbattles.quests.quest;

import com.worldbattles.quests.entity.QuestNpc;
import com.worldbattles.quests.network.ModNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public final class QuestService {
    public static void interact(ServerPlayer player, QuestNpc npc) {
        open(player, npc.questNpcId());
    }

    /** Opens the client quest interface using the player's server-side saved progress. */
    public static void open(ServerPlayer player, String npcId) {
        if (!validNpc(npcId)) return;
        QuestSavedData saved = QuestSavedData.get(player.serverLevel());
        QuestSavedData.PlayerData data = saved.player(player.getUUID());
        QuestDefinition quest = QuestCatalog.current(npcId, data.completed(npcId));
        String npcName = npcName(npcId);
        if (quest == null) {
            ModNetworking.openQuestScreen(player, npcId, npcName,
                "Toutes les quêtes terminées", "Tu as terminé toutes les quêtes de " + npcName + ".",
                0, 0, 0, false, true);
            return;
        }
        ModNetworking.openQuestScreen(player, npcId, npcName, quest.title(), quest.description(),
            data.progress(quest.id()), quest.count(), quest.reward(),
            quest.type() == QuestDefinition.Type.DELIVER_ITEM, false);
    }

    public static void donate(ServerPlayer player, String npcId) {
        if (!validNpc(npcId)) return;
        QuestSavedData saved = QuestSavedData.get(player.serverLevel());
        QuestSavedData.PlayerData data = saved.player(player.getUUID());
        QuestDefinition quest = QuestCatalog.current(npcId, data.completed(npcId));
        if (quest == null || quest.type() != QuestDefinition.Type.DELIVER_ITEM) return;
        Item item = ForgeRegistries.ITEMS.getValue(quest.target());
        if (item == null) return;
        int remaining = Math.max(0, quest.count() - data.progress(quest.id()));
        int amount = Math.min(remaining, count(player, item));
        if (amount <= 0) return;
        remove(player, item, amount);
        data.add(quest.id(), amount);
        saved.setDirty();
        player.getInventory().setChanged();
    }

    public static void claim(ServerPlayer player, String npcId) {
        if (!validNpc(npcId)) return;
        QuestSavedData saved = QuestSavedData.get(player.serverLevel());
        QuestSavedData.PlayerData data = saved.player(player.getUUID());
        QuestDefinition quest = QuestCatalog.current(npcId, data.completed(npcId));
        if (quest == null || data.progress(quest.id()) < quest.count()) return;
        if (!ShopEconomy.deposit(player, quest.reward())) return;
        data.finish(npcId, quest.id());
        saved.setDirty();
    }

    private static boolean validNpc(String id) {
        return id != null && (id.equals("blacksmith") || id.equals("explorer") || id.equals("hunter"));
    }

    private static String npcName(String id) {
        return switch (id) {
            case "blacksmith" -> "Jarvis le Forgeron";
            case "explorer" -> "Exploratrice";
            default -> "Chasseur";
        };
    }

    private static int count(ServerPlayer player, Item item) {
        int count = 0;
        for (ItemStack stack : player.getInventory().items) if (stack.is(item)) count += stack.getCount();
        return count;
    }

    private static void remove(ServerPlayer player, Item item, int amount) {
        for (ItemStack stack : player.getInventory().items) {
            if (amount <= 0) break;
            if (stack.is(item)) {
                int removed = Math.min(amount, stack.getCount());
                stack.shrink(removed);
                amount -= removed;
            }
        }
    }

    private QuestService() {}
}