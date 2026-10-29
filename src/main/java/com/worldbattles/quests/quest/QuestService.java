package com.worldbattles.quests.quest;
import com.worldbattles.quests.entity.QuestNpc;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
public final class QuestService {
 public static void interact(ServerPlayer p, QuestNpc npc) {
  QuestSavedData saved=QuestSavedData.get(p.serverLevel());QuestSavedData.PlayerData data=saved.player(p.getUUID());
  QuestDefinition q=QuestCatalog.current(npc.questNpcId(),data.completed(npc.questNpcId()));
  if(q==null){p.sendSystemMessage(Component.literal(npc.displayName()+" : tu as terminé toutes mes quêtes !").withStyle(ChatFormatting.GOLD));return;}
  show(p,npc.questNpcId(),npc.displayName(),q,data.progress(q.id()));
 }
 public static void donate(ServerPlayer p,String npcId){
  QuestSavedData saved=QuestSavedData.get(p.serverLevel());QuestSavedData.PlayerData data=saved.player(p.getUUID());
  QuestDefinition q=QuestCatalog.current(npcId,data.completed(npcId));
  if(q==null){p.sendSystemMessage(Component.literal("Tu as terminé cette série de quêtes.").withStyle(ChatFormatting.GOLD));return;}
  if(q.type()!=QuestDefinition.Type.DELIVER_ITEM){show(p,npcId,npcName(npcId),q,data.progress(q.id()));return;}
  Item item=ForgeRegistries.ITEMS.getValue(q.target());
  if(item==null){p.sendSystemMessage(Component.literal("Objet de quête introuvable : "+q.target()).withStyle(ChatFormatting.RED));return;}
  int remaining=Math.max(0,q.count()-data.progress(q.id()));int amount=Math.min(remaining,count(p,item));
  if(amount>0){remove(p,item,amount);data.add(q.id(),amount);saved.setDirty();p.getInventory().setChanged();}
  int progress=data.progress(q.id());
  p.sendSystemMessage(Component.literal(amount>0?"Jarvis a stocké "+amount+" objet(s) pour cette commande.":"Tu n'as aucun objet requis à apporter pour le moment.").withStyle(amount>0?ChatFormatting.GREEN:ChatFormatting.YELLOW));
  show(p,npcId,npcName(npcId),q,progress);
 }
 public static void claim(ServerPlayer p,String npcId){
  QuestSavedData saved=QuestSavedData.get(p.serverLevel());QuestSavedData.PlayerData data=saved.player(p.getUUID());
  QuestDefinition q=QuestCatalog.current(npcId,data.completed(npcId));
  if(q==null){p.sendSystemMessage(Component.literal("Tu as déjà terminé cette série de quêtes.").withStyle(ChatFormatting.GOLD));return;}
  int progress=data.progress(q.id());
  if(progress<q.count()){p.sendSystemMessage(Component.literal("Impossible de réclamer : il manque encore "+(q.count()-progress)+" unité(s).").withStyle(ChatFormatting.RED));show(p,npcId,npcName(npcId),q,progress);return;}
  if(!ShopEconomy.deposit(p,q.reward())){p.sendSystemMessage(Component.literal("Récompense non versée : impossible d'accéder au solde WorldBattles Shop. Ta progression est conservée.").withStyle(ChatFormatting.RED));return;}
  data.finish(npcId,q.id());saved.setDirty();p.sendSystemMessage(Component.literal("Récompense réclamée : +"+q.reward()+" $ versés sur ton solde WorldBattles Shop !").withStyle(ChatFormatting.GREEN));
  QuestDefinition next=QuestCatalog.current(npcId,data.completed(npcId));if(next!=null)show(p,npcId,npcName(npcId),next,data.progress(next.id()));else p.sendSystemMessage(Component.literal("Tu as terminé toutes les quêtes de "+npcName(npcId)+" !").withStyle(ChatFormatting.GOLD));
 }
 private static void show(ServerPlayer p,String npcId,String npcName,QuestDefinition q,int progress){
  p.sendSystemMessage(Component.literal("━━━━━━ "+npcName+" · "+q.title()+" ━━━━━━").withStyle(ChatFormatting.GOLD,ChatFormatting.BOLD));
  p.sendSystemMessage(Component.literal(q.description()));
  int shown=Math.min(progress,q.count());int filled=q.count()==0?10:Math.min(10,(int)Math.floor(shown*10.0/q.count()));
  p.sendSystemMessage(Component.literal("Progression : "+"█".repeat(filled)+"░".repeat(10-filled)+"  "+shown+" / "+q.count()+"   |   Récompense : "+q.reward()+" $").withStyle(ChatFormatting.YELLOW));
  if(q.type()==QuestDefinition.Type.DELIVER_ITEM&&progress<q.count()){
   Component donate=Component.literal("[ APPORTER LES OBJETS ]").withStyle(s->s.withColor(ChatFormatting.GREEN).withBold(true).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/wbquests donate "+npcId)).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,Component.literal("Dépôts partiels conservés."))));p.sendSystemMessage(donate);
  }
  if(progress>=q.count()){
   Component claim=Component.literal("[ RÉCLAMER LA RÉCOMPENSE ]").withStyle(s->s.withColor(ChatFormatting.GOLD).withBold(true).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/wbquests claim "+npcId)).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,Component.literal("Ajoute l'argent au solde WorldBattles Shop."))));p.sendSystemMessage(claim);
  }
 }
 private static String npcName(String id){return switch(id){case "blacksmith"->"Jarvis";case "explorer"->"Exploratrice";default->"Chasseur";};}
 private static int count(ServerPlayer p,Item item){int n=0;for(ItemStack s:p.getInventory().items)if(s.is(item))n+=s.getCount();return n;}
 private static void remove(ServerPlayer p,Item item,int amount){for(ItemStack s:p.getInventory().items){if(amount<=0)break;if(s.is(item)){int n=Math.min(amount,s.getCount());s.shrink(n);amount-=n;}}}
 private QuestService(){}
}
