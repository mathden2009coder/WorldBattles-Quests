package com.worldbattles.quests.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

/** Dr. K. Driver, the hunter quest giver. */
public class HunterNpc extends QuestNpc {
    private static final byte STATE_IDLE = 0;
    private static final byte STATE_INSPECT = 1;
    private static final byte STATE_TALK = 2;

    private static final EntityDataAccessor<Byte> ANIMATION_STATE =
        SynchedEntityData.defineId(HunterNpc.class, EntityDataSerializers.BYTE);

    private static final RawAnimation IDLE =
        RawAnimation.begin().thenLoop("animation.hunter.idle_animation");
    private static final RawAnimation TALK =
        RawAnimation.begin().thenPlay("animation.hunter.talking");
    private static final RawAnimation INSPECT =
        RawAnimation.begin().thenPlay("animation.hunter.inspecting_sword");

    // Exact lengths from hunter.animation.json, converted to 20 game ticks/second.
    private static final int IDLE_CYCLE_TICKS = 108; // 5.375 seconds
    private static final int TALK_TICKS = 60;        // 3 seconds
    private static final int INSPECT_TICKS = 50;     // 2.5 seconds
    private static final int[] IDLE_CYCLES_BETWEEN_INSPECTIONS = {3, 2, 2};

    private int stateTicksRemaining;
    private int inspectionIntervalIndex;

    public HunterNpc(EntityType<? extends HunterNpc> type, Level level) {
        super(type, level);
        this.stateTicksRemaining = IDLE_CYCLE_TICKS
            * IDLE_CYCLES_BETWEEN_INSPECTIONS[0];
    }

    @Override public String questNpcId() { return "hunter"; }
    @Override public String displayName() { return "Dr. K. Driver le Traqueur"; }
    @Override public String idleAnimation() { return "animation.hunter.idle_animation"; }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, STATE_IDLE);
    }

    private byte getAnimationState() {
        return this.entityData.get(ANIMATION_STATE);
    }

    private void setAnimationState(byte state, int duration) {
        this.entityData.set(ANIMATION_STATE, state);
        this.stateTicksRemaining = duration;
    }

    /** Play the talking clip; after it finishes, idle starts again from frame zero. */
    public void startTalking() {
        if (level().isClientSide) return;
        setAnimationState(STATE_TALK, TALK_TICKS);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) return;

        if (--stateTicksRemaining > 0) return;

        byte state = getAnimationState();
        if (state == STATE_TALK || state == STATE_INSPECT) {
            // Returning to IDLE switches the single controller to the idle clip,
            // which starts from its first frame rather than resuming mid-cycle.
            int idleCycles = IDLE_CYCLES_BETWEEN_INSPECTIONS[inspectionIntervalIndex];
            setAnimationState(STATE_IDLE, IDLE_CYCLE_TICKS * idleCycles);
            if (state == STATE_INSPECT) {
                inspectionIntervalIndex = (inspectionIntervalIndex + 1)
                    % IDLE_CYCLES_BETWEEN_INSPECTIONS.length;
            }
        } else {
            // The complete idle loop(s) have elapsed: play inspection exactly once.
            setAnimationState(STATE_INSPECT, INSPECT_TICKS);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // One controller owns all three clips, so their bone transforms can never
        // overlap or blend with a second controller.
        controllers.add(new AnimationController<>(this, "hunter_main", 0, state -> {
            byte animationState = state.getAnimatable().getAnimationState();
            if (animationState == STATE_TALK) return state.setAndContinue(TALK);
            if (animationState == STATE_INSPECT) return state.setAndContinue(INSPECT);
            return state.setAndContinue(IDLE);
        }));
    }
}
