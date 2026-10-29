package com.worldbattles.quests;

import com.worldbattles.quests.registry.ModEntities;
import com.worldbattles.quests.quest.QuestEvents;
import com.worldbattles.quests.quest.QuestCommands;
import net.minecraftforge.common.MinecraftForge;
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
        MinecraftForge.EVENT_BUS.register(new QuestEvents());
        MinecraftForge.EVENT_BUS.register(new QuestCommands());
    }
}
