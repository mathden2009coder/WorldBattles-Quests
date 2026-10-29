package com.worldbattles.quests.entity;
import net.minecraft.world.entity.EntityType;import net.minecraft.world.level.Level;
public class ExplorerNpc extends QuestNpc{public ExplorerNpc(EntityType<? extends ExplorerNpc> t,Level l){super(t,l);}public String questNpcId(){return "explorer";}public String displayName(){return "Mira l'Exploratrice";}public String idleAnimation(){return "animation.explorer.idle";}}
