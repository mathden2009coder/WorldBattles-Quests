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
    private static final EntityDataAccessor<Boolean> ACTION_ACTIVE =
        net.minecraft.network.syncher.SynchedEntityData.defineId(HunterNpc.class, EntityDataSerializers.BOOLEAN);

    private static final RawAnimation IDLE =
        RawAnimation.begin().thenLoop("animation.hunter.idle_animation");
    private static final RawAnimation TALK =
        RawAnimation.begin().thenPlay("animation.hunter.talking");
    private static final RawAnimation INSPECT =
        RawAnimation.begin().thenPlay("animation.hunter.inspecting_sword");

    // The idle clip is 5.375 seconds (about 108 game ticks).
    private static final int IDLE_CYCLE_TICKS = 108;
    private static final int TALK_TICKS = 60;       // 3 seconds
    private static final int INSPECT_TICKS = 50;    // 2.5 seconds
    private static final int[] IDLE_CYCLES_BETWEEN_INSPECTIONS = {3, 2, 2};

    private int actionTicks;
    private int ticksUntilInspection = IDLE_CYCLE_TICKS * 2;
    private int inspectionIntervalIndex;

    public HunterNpc(EntityType<? extends HunterNpc> type, Level level) {
        super(type, level);
    }

    @Override public String questNpcId() { return "hunter"; }
    @Override public String displayName() { return "Dr. K. Driver le Traqueur"; }
    @Override public String idleAnimation() { return "animation.hunter.idle_animation"; }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ACTION_ACTIVE, false);
    }

    public boolean isActionActive() {
        return this.entityData.get(ACTION_ACTIVE);
    }

    /** Starts the conversation clip and temporarily suspends idle/inspection. */
    public void startTalking() {
        if (level().isClientSide) return;
        startAction(TALK_TICKS, "talk");
    }

    private void startAction(int duration, String trigger) {
        this.entityData.set(ACTION_ACTIVE, true);
        this.actionTicks = duration;
        triggerAnim("actions", trigger);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) return;

        if (isActionActive()) {
            if (--actionTicks <= 0) {
                this.entityData.set(ACTION_ACTIVE, false);
            }
            return;
        }

        // Idle sequence: idle, idle, inspect, idle, idle, idle, inspect,
        // idle, idle, inspect, then repeat the 3/2/2 idle-cycle intervals.
        if (--ticksUntilInspection <= 0) {
            startAction(INSPECT_TICKS, "inspect");
            int idleCycles = IDLE_CYCLES_BETWEEN_INSPECTIONS[inspectionIntervalIndex];
            inspectionIntervalIndex = (inspectionIntervalIndex + 1)
                % IDLE_CYCLES_BETWEEN_INSPECTIONS.length;
            ticksUntilInspection = IDLE_CYCLE_TICKS * idleCycles;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle", 5, state -> {
            if (state.getAnimatable().isActionActive()) return PlayState.STOP;
            return state.setAndContinue(IDLE);
        }));
        controllers.add(new AnimationController<>(this, "actions", 0, state -> PlayState.STOP)
            .triggerableAnim("talk", TALK)
            .triggerableAnim("inspect", INSPECT));
    }
}
