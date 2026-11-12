package com.worldbattles.quests.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

/** Maylline, the quiet keeper of the lost archives. */
public class MayllineNpc extends QuestNpc {
    private static final byte STATE_IDLE = 0;
    private static final byte STATE_READING = 1;
    private static final byte STATE_TALK = 2;

    private static final EntityDataAccessor<Byte> ANIMATION_STATE =
        SynchedEntityData.defineId(MayllineNpc.class, EntityDataSerializers.BYTE);

    private static final RawAnimation IDLE =
        RawAnimation.begin().thenLoop("animation.maylline.idle");
    private static final RawAnimation READING =
        RawAnimation.begin().thenPlay("animation.maylline.reading");
    private static final RawAnimation TALK =
        RawAnimation.begin().thenPlay("animation.maylline.talking");

    private static final int IDLE_CYCLE_TICKS = 90; // 4.5 seconds
    private static final int READING_TICKS = 150;   // 7.5 seconds
    private static final int TALK_TICKS = 40;       // 2 seconds
    private static final int[] IDLE_CYCLES_BETWEEN_READINGS = {2, 3, 2};

    private int stateTicksRemaining;
    private int readingIntervalIndex;

    public MayllineNpc(EntityType<? extends MayllineNpc> type, Level level) {
        super(type, level);
        this.stateTicksRemaining = IDLE_CYCLE_TICKS * IDLE_CYCLES_BETWEEN_READINGS[0];
    }

    @Override public String questNpcId() { return "maylline"; }
    @Override public String displayName() { return "Maylline — Gardienne des Archives"; }
    @Override public String idleAnimation() { return "animation.maylline.idle"; }

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

    /** The talking clip takes priority and returns to the idle loop afterwards. */
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
        if (state == STATE_TALK) {
            int idleCycles = IDLE_CYCLES_BETWEEN_READINGS[readingIntervalIndex];
            setAnimationState(STATE_IDLE, IDLE_CYCLE_TICKS * idleCycles);
        } else if (state == STATE_READING) {
            readingIntervalIndex = (readingIntervalIndex + 1)
                % IDLE_CYCLES_BETWEEN_READINGS.length;
            int idleCycles = IDLE_CYCLES_BETWEEN_READINGS[readingIntervalIndex];
            setAnimationState(STATE_IDLE, IDLE_CYCLE_TICKS * idleCycles);
        } else {
            setAnimationState(STATE_READING, READING_TICKS);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // One controller owns the three clips so their bone transforms never overlap.
        controllers.add(new AnimationController<>(this, "maylline_main", 0, state -> {
            byte animationState = state.getAnimatable().getAnimationState();
            if (animationState == STATE_TALK) return state.setAndContinue(TALK);
            if (animationState == STATE_READING) return state.setAndContinue(READING);
            return state.setAndContinue(IDLE);
        }));
    }
}
