package com.worldbattles.quests.quest;

import com.worldbattles.quests.entity.QuestNpc;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class QuestEvents {
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        Entity victim = event.getEntity();
        if (victim instanceof Player || victim instanceof QuestNpc) return;

        ServerPlayer killer = findPlayerKiller(event);
        if (killer == null) return;

        QuestSavedData saved = QuestSavedData.get(killer.serverLevel());
        QuestSavedData.PlayerData data = saved.player(killer.getUUID());
        QuestDefinition quest = QuestCatalog.current("hunter", data.completed("hunter"));
        if (quest == null || quest.type() != QuestDefinition.Type.KILL_ENTITY) return;

        if (Objects.equals(ForgeRegistries.ENTITY_TYPES.getKey(victim.getType()), quest.target())) {
            data.add(quest.id(), 1);
            saved.setDirty();
        }
    }

    private static ServerPlayer findPlayerKiller(LivingDeathEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (attacker instanceof ServerPlayer player) return player;

        Entity direct = event.getSource().getDirectEntity();
        if (direct instanceof ServerPlayer player) return player;
        if (direct instanceof Projectile projectile && projectile.getOwner() instanceof ServerPlayer player) {
            return player;
        }
        if (attacker instanceof Projectile projectile && projectile.getOwner() instanceof ServerPlayer player) {
            return player;
        }
        return null;
    }
}
