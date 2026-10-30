package com.worldbattles.quests.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

/** Dr. K. Driver, the hunter quest giver. */
public class HunterNpc extends QuestNpc {
    private static final RawAnimation IDLE =
        RawAnimation.begin().thenLoop("animation.hunter.idle_animation");
    private static final RawAnimation TALK =
        RawAnimation.begin().thenPlay("animation.hunter.talking");
    private static final RawAnimation INSPECT =
        RawAnimation.begin().thenPlay("animation.hunter.inspecting_sword");

    public HunterNpc(EntityType<? extends HunterNpc> type, Level level) {
        super(type, level);
    }

    @Override public String questNpcId() { return "hunter"; }
    @Override public String displayName() { return "Dr. K. Driver le Traqueur"; }
    @Override public String idleAnimation() { return "animation.hunter.idle_animation"; }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle", 5,
            state -> state.setAndContinue(IDLE)));
        controllers.add(new AnimationController<>(this, "actions", 0, state -> PlayState.STOP)
            .triggerableAnim("talk", TALK)
            .triggerableAnim("inspect", INSPECT));
    }
}
