package com.worldbattles.quests.client.model;

import com.worldbattles.quests.WorldBattlesQuests;
import com.worldbattles.quests.entity.MayllineNpc;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MayllineModel extends GeoModel<MayllineNpc> {
    @Override
    public ResourceLocation getModelResource(MayllineNpc entity) {
        return new ResourceLocation(WorldBattlesQuests.MODID, "geo/maylline.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MayllineNpc entity) {
        return new ResourceLocation(WorldBattlesQuests.MODID, "textures/entity/maylline.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MayllineNpc entity) {
        return new ResourceLocation(WorldBattlesQuests.MODID, "animations/maylline.animation.json");
    }
}
