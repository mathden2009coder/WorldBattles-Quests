package com.worldbattles.quests.quest;
import net.minecraft.resources.ResourceLocation;
public record QuestDefinition(String npcId,int index,String title,String description,Type type,ResourceLocation target,int count,long reward) {
 public enum Type { DELIVER_ITEM, KILL_ENTITY }
 public String id(){return npcId+"_"+String.format("%02d",index);}
}
