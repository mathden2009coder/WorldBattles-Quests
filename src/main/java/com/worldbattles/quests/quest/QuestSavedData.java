package com.worldbattles.quests.quest;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import java.util.*;
public class QuestSavedData extends SavedData {
 private static final String NAME="worldbattles_quests_progress";
 private final Map<String,PlayerData> players=new HashMap<>();
 public static QuestSavedData get(ServerLevel level){return level.getServer().overworld().getDataStorage().computeIfAbsent(QuestSavedData::load,QuestSavedData::new,NAME);}
 public static QuestSavedData load(CompoundTag root){QuestSavedData d=new QuestSavedData();CompoundTag all=root.getCompound("Players");for(String k:all.getAllKeys()){CompoundTag p=all.getCompound(k);PlayerData pd=new PlayerData();pd.completed.putAll(read(p.getCompound("Completed")));pd.progress.putAll(read(p.getCompound("Progress")));pd.wallet=p.getLong("Wallet");d.players.put(k,pd);}return d;}
 private static Map<String,Integer> read(CompoundTag t){Map<String,Integer> m=new HashMap<>();for(String k:t.getAllKeys())m.put(k,t.getInt(k));return m;}
 @Override public CompoundTag save(CompoundTag root){CompoundTag all=new CompoundTag();players.forEach((k,v)->{CompoundTag p=new CompoundTag(),c=new CompoundTag(),g=new CompoundTag();v.completed.forEach(c::putInt);v.progress.forEach(g::putInt);p.put("Completed",c);p.put("Progress",g);p.putLong("Wallet",v.wallet);all.put(k,p);});root.put("Players",all);return root;}
 public PlayerData player(UUID id){setDirty();return players.computeIfAbsent(id.toString(),k->new PlayerData());}
 public static class PlayerData {public Map<String,Integer> completed=new HashMap<>(),progress=new HashMap<>();public long wallet;public int completed(String npc){return completed.getOrDefault(npc,0);}public int progress(String id){return progress.getOrDefault(id,0);}public void add(String id,int n){progress.merge(id,n,Integer::sum);}public void finish(String npc,String id){completed.put(npc,completed(npc)+1);progress.remove(id);}}
}
