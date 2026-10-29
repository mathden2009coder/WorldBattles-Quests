package com.worldbattles.quests.entity;
import net.minecraft.world.entity.EntityType;import net.minecraft.world.level.Level;
public class HunterNpc extends QuestNpc{public HunterNpc(EntityType<? extends HunterNpc> t,Level l){super(t,l);}public String questNpcId(){return "hunter";}public String displayName(){return "Kael le Chasseur";}public String idleAnimation(){return "animation.hunter.idle";}}
