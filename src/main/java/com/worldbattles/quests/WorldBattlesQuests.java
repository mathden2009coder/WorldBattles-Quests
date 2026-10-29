package com.worldbattles.quests;

import com.worldbattles.quests.registry.ModEntities;
import com.worldbattles.quests.quest.QuestEvents;
import com.worldbattles.quests.quest.QuestCommands;
import com.worldbattles.quests.entity.BlacksmithNpc;
import com.worldbattles.quests.entity.ExplorerNpc;
import com.worldbattles.quests.entity.HunterNpc;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib.GeckoLib;

@Mod(WorldBattlesQuests.MODID)
public class WorldBattlesQuests {
    public static final String MODID = "worldbattles_quests";
    public WorldBattlesQuests() {
        GeckoLib.initialize();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.TYPES.register(bus);
        bus.addListener(this::registerAttributes);
        MinecraftForge.EVENT_BUS.register(new QuestEvents());
        MinecraftForge.EVENT_BUS.register(new QuestCommands());
    }
    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BLACKSMITH.get(), BlacksmithNpc.createAttributes().build());
        event.put(ModEntities.EXPLORER.get(), ExplorerNpc.createAttributes().build());
        event.put(ModEntities.HUNTER.get(), HunterNpc.createAttributes().build());
    }
}
