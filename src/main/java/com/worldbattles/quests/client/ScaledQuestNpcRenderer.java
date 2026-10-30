package com.worldbattles.quests.client;

import com.worldbattles.quests.entity.QuestNpc;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

/** Geo renderer that applies an additional per-NPC visual scale. */
public class ScaledQuestNpcRenderer<T extends QuestNpc & GeoAnimatable> extends GeoEntityRenderer<T> {
    private final float extraScale;

    public ScaledQuestNpcRenderer(EntityRendererProvider.Context context, GeoModel<T> model, float extraScale) {
        super(context, model);
        this.extraScale = extraScale;
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack,
                                    T animatable, BakedGeoModel model, boolean isReRender,
                                    float partialTick, int packedLight, int packedOverlay) {
        super.scaleModelForRender(widthScale * extraScale, heightScale * extraScale, poseStack,
            animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }
}
