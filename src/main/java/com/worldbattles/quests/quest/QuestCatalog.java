package com.worldbattles.quests.quest;
import net.minecraft.resources.ResourceLocation;
import java.util.*;
public final class QuestCatalog {
 private static final Map<String,List<QuestDefinition>> CHAINS=new LinkedHashMap<>();
 static {
  add("blacksmith","Commandes de forge",QuestDefinition.Type.DELIVER_ITEM,new String[]{"coal","raw_iron","iron_ingot","iron_pickaxe","iron_sword","raw_copper","copper_ingot","gold_ingot","iron_block","diamond","diamond_pickaxe","diamond_sword","obsidian","anvil","gold_block","emerald","diamond_block","netherite_scrap","netherite_ingot","netherite_block"},new int[]{8,12,16,1,1,20,16,12,3,4,1,1,24,1,4,12,2,8,2,1});
  add("explorer","Carnet d'exploration",QuestDefinition.Type.DELIVER_ITEM,new String[]{"bread","compass","map","oak_log","ender_pearl","boat","spyglass","lapis_lazuli","golden_apple","amethyst_shard","blaze_rod","ender_eye","ghast_tear","shulker_shell","elytra","heart_of_the_sea","totem_of_undying","nether_star","dragon_breath","dragon_egg"},new int[]{8,1,2,32,4,2,1,16,2,12,8,8,4,4,1,1,1,1,8,1});
  add("hunter","Contrats de chasse",QuestDefinition.Type.KILL_ENTITY,new String[]{"zombie","skeleton","spider","creeper","drowned","enderman","witch","pillager","phantom","slime","blaze","ghast","hoglin","piglin_brute","shulker","guardian","ravager","evoker","wither_skeleton","warden"},new int[]{5,6,8,8,10,4,4,8,6,12,8,3,10,4,5,8,3,3,10,1});
 }
 private static void add(String npc,String theme,QuestDefinition.Type type,String[] targets,int[] counts){
  List<QuestDefinition> list=new ArrayList<>();
  for(int i=0;i<20;i++){long reward=new long[]{100,150,250,350,500,700,900,1200,1600,2200,3000,4200,6000,8500,12000,18000,27000,40000,65000,100000}[i];String label=targets[i].replace('_',' ');String desc=type==QuestDefinition.Type.KILL_ENTITY?"Élimine "+counts[i]+" "+label+".":"Rapporte "+counts[i]+" "+label+".";list.add(new QuestDefinition(npc,i+1,theme+" "+(i+1),desc,type,new ResourceLocation("minecraft:"+targets[i]),counts[i],reward));}
  CHAINS.put(npc,List.copyOf(list));
 }
 public static List<QuestDefinition> forNpc(String id){return CHAINS.getOrDefault(id,List.of());}
 public static QuestDefinition current(String id,int completed){List<QuestDefinition> l=forNpc(id);return completed>=l.size()?null:l.get(completed);}
 private QuestCatalog(){}
}
