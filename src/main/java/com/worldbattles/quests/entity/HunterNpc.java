package com.worldbattles.quests.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** Dr. K. Driver, the hunter quest giver. */
public class HunterNpc extends QuestNpc {
    public HunterNpc(EntityType<? extends HunterNpc> type, Level level) {
        super(type, level);
    }

    @Override public String questNpcId() { return "hunter"; }
    @Override public String displayName() { return "Dr. K. Driver le Traqueur"; }
    @Override public String idleAnimation() { return "animation.hunter.idle_animation"; }
}
