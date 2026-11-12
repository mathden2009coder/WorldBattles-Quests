package com.worldbattles.quests.client;

import com.worldbattles.quests.WorldBattlesQuests;
import com.worldbattles.quests.client.model.BlacksmithModel;
import com.worldbattles.quests.client.model.HunterModel;
import com.worldbattles.quests.client.model.MayllineModel;
import com.worldbattles.quests.registry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Mod.EventBusSubscriber(modid = WorldBattlesQuests.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {
    private ClientModEvents() {}

    @SubscribeEvent
    public static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BLACKSMITH.get(),
            context -> new ScaledQuestNpcRenderer<>(context, new BlacksmithModel(), 1.5f));
        event.registerEntityRenderer(ModEntities.HUNTER.get(),
            context -> new ScaledQuestNpcRenderer<>(context, new HunterModel(), 1.25f));
        event.registerEntityRenderer(ModEntities.MAYLLINE.get(),
            context -> new ScaledQuestNpcRenderer<>(context, new MayllineModel(), 0.6f));
    }
}
