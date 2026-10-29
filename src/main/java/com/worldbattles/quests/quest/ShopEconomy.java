package com.worldbattles.quests.quest;

import net.minecraft.server.level.ServerPlayer;
import java.lang.reflect.Method;

/** Uses reflection so the source builds without a compile-time Shop jar. */
public final class ShopEconomy {
    public static boolean deposit(ServerPlayer player, long amount) {
        if (amount <= 0 || player.getServer() == null) return false;
        try {
            Class<?> shopDataClass = Class.forName("com.worldbattles.shop.ShopData");
            Method get = shopDataClass.getMethod("get", net.minecraft.server.MinecraftServer.class);
            Object shop = get.invoke(null, player.getServer());
            shopDataClass.getMethod("remember", ServerPlayer.class).invoke(shop, player);
            Class<?> resultClass = Class.forName("com.worldbattles.shop.ShopData$Result");
            Method adminAdd = shopDataClass.getMethod("adminAdd", java.util.UUID.class, String.class, long.class);
            Object result = adminAdd.invoke(shop, player.getUUID(), player.getGameProfile().getName(), amount);
            if (result == null) return false;
            Method ok = resultClass.getMethod("ok");
            return Boolean.TRUE.equals(ok.invoke(result));
        } catch (Throwable ex) {
            System.err.println("[WorldBattlesQuests] Impossible d'ajouter la récompense au solde Shop : " + ex);
            return false;
        }
    }
    private ShopEconomy() {}
}