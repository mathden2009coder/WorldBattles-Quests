package com.worldbattles.quests.entity;
import net.minecraft.world.entity.EntityType; import net.minecraft.world.level.Level;
public class BlacksmithNpc extends QuestNpc { public BlacksmithNpc(EntityType<? extends BlacksmithNpc> t,Level l){super(t,l);} public String questNpcId(){return "blacksmith";} public String displayName(){return "Jarvis le Forgeron";} public String idleAnimation(){return "animation.blacksmith.idle";} }
