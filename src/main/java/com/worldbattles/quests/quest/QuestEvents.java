package com.worldbattles.quests.quest;
import com.worldbattles.quests.entity.QuestNpc;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
public class QuestEvents {
 @SubscribeEvent public void onDeath(LivingDeathEvent e){
  if(!(e.getSource().getEntity() instanceof ServerPlayer p)||e.getEntity() instanceof Player||e.getEntity() instanceof QuestNpc)return;
  QuestSavedData saved=QuestSavedData.get(p.serverLevel());QuestSavedData.PlayerData d=saved.player(p.getUUID());
  QuestDefinition q=QuestCatalog.current("hunter",d.completed("hunter"));
  if(q!=null&&q.type()==QuestDefinition.Type.KILL_ENTITY&&ForgeRegistries.ENTITY_TYPES.getKey(e.getEntity().getType()).equals(q.target())){d.add(q.id(),1);saved.setDirty();}
 }
}
