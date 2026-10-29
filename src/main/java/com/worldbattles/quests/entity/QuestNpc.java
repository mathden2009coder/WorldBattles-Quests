package com.worldbattles.quests.entity;
import com.worldbattles.quests.quest.QuestService;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
public abstract class QuestNpc extends PathfinderMob implements GeoEntity {
 private final AnimatableInstanceCache cache=GeckoLibUtil.createInstanceCache(this);
 protected QuestNpc(EntityType<? extends PathfinderMob> type,Level level){super(type,level);setPersistenceRequired();setNoAi(true);setDeltaMovement(0,0,0);xpReward=0;}
 public abstract String questNpcId();
 public abstract String displayName();
 public abstract String idleAnimation();
 public static AttributeSupplier.Builder createAttributes(){return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH,40).add(Attributes.MOVEMENT_SPEED,0).add(Attributes.KNOCKBACK_RESISTANCE,1);}
 @Override protected InteractionResult mobInteract(Player player,InteractionHand hand){
  if(hand!=InteractionHand.MAIN_HAND)return InteractionResult.PASS;
  if(level().isClientSide)return InteractionResult.SUCCESS;
  if(player instanceof ServerPlayer sp) {
   if(this instanceof BlacksmithNpc) triggerAnim("actions","talk");
   QuestService.interact(sp,this);
  }
  return InteractionResult.CONSUME;
 }
 @Override public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
  controllers.add(new AnimationController<>(this,"idle",5,state->state.setAndContinue(RawAnimation.begin().thenLoop(idleAnimation()))));
  controllers.add(new AnimationController<>(this,"actions",0,state->PlayState.STOP)
    .triggerableAnim("talk",RawAnimation.begin().thenPlay("animation.blacksmith.talk"))
    .triggerableAnim("work",RawAnimation.begin().thenPlay("animation.blacksmith.work")));
 }
 @Override public AnimatableInstanceCache getAnimatableInstanceCache(){return cache;}
 @Override public boolean removeWhenFarAway(double distance){return false;}
 @Override public boolean isPushable(){return false;}
 @Override protected void doPush(net.minecraft.world.entity.Entity entity){}
 @Override public void push(double x,double y,double z){}
 @Override public boolean hurt(net.minecraft.world.damagesource.DamageSource source,float amount){return false;}
 @Override protected void registerGoals(){}
}
