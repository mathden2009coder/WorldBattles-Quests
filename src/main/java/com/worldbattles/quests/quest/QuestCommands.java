package com.worldbattles.quests.quest;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.worldbattles.quests.entity.QuestNpc;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Comparator;

public final class QuestCommands {
    @SubscribeEvent
    public void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("wbquests")
                .then(Commands.literal("remove")
                    .requires(source -> source.hasPermission(2))
                    .then(Commands.argument("npc", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            builder.suggest("blacksmith");
                            builder.suggest("explorer");
                            builder.suggest("hunter");
                            return builder.buildFuture();
                        })
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            String id = StringArgumentType.getString(ctx, "npc");
                            if (!valid(id)) {
                                ctx.getSource().sendFailure(Component.literal("PNJ inconnu. Utilise blacksmith, explorer ou hunter."));
                                return 0;
                            }
                            QuestNpc npc = player.serverLevel()
                                .getEntitiesOfClass(QuestNpc.class, player.getBoundingBox().inflate(64),
                                    entity -> entity.questNpcId().equals(id))
                                .stream()
                                .min(Comparator.comparingDouble(player::distanceToSqr))
                                .orElse(null);
                            if (npc == null) {
                                ctx.getSource().sendFailure(Component.literal("Aucun PNJ " + id + " trouvé à moins de 64 blocs."));
                                return 0;
                            }
                            npc.discard();
                            ctx.getSource().sendSuccess(() -> Component.literal("PNJ " + id + " supprimé."), true);
                            return 1;
                        })))
                .then(Commands.literal("turn")
                    .requires(source -> source.hasPermission(2))
                    .then(Commands.argument("npc", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            builder.suggest("blacksmith");
                            builder.suggest("explorer");
                            builder.suggest("hunter");
                            return builder.buildFuture();
                        })
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            String id = StringArgumentType.getString(ctx, "npc");
                            if (!valid(id)) {
                                ctx.getSource().sendFailure(Component.literal("PNJ inconnu. Utilise blacksmith, explorer ou hunter."));
                                return 0;
                            }
                            QuestNpc npc = player.serverLevel()
                                .getEntitiesOfClass(QuestNpc.class, player.getBoundingBox().inflate(64),
                                    entity -> entity.questNpcId().equals(id))
                                .stream()
                                .min(Comparator.comparingDouble(player::distanceToSqr))
                                .orElse(null);
                            if (npc == null) {
                                ctx.getSource().sendFailure(Component.literal("Aucun PNJ " + id + " trouvé à moins de 64 blocs."));
                                return 0;
                            }
                            // In Minecraft yaw, subtracting 45 degrees turns the NPC to the right.
                            float yaw = npc.getYRot() - 45.0F;
                            npc.setYRot(yaw);
                            npc.setYHeadRot(yaw);
                            npc.setYBodyRot(yaw);
                            npc.setYRot(yaw);
                            ctx.getSource().sendSuccess(() -> Component.literal("PNJ " + id + " tourné de 45° vers la droite."), true);
                            return 1;
                        })))
                .then(Commands.literal("donate")
                    .then(Commands.argument("npc", StringArgumentType.word())
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            String id = StringArgumentType.getString(ctx, "npc");
                            if (!valid(id)) {
                                ctx.getSource().sendFailure(Component.literal("PNJ inconnu."));
                                return 0;
                            }
                            QuestService.donate(player, id);
                            return 1;
                        })))
                .then(Commands.literal("claim")
                    .then(Commands.argument("npc", StringArgumentType.word())
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            String id = StringArgumentType.getString(ctx, "npc");
                            if (!valid(id)) {
                                ctx.getSource().sendFailure(Component.literal("PNJ inconnu."));
                                return 0;
                            }
                            QuestService.claim(player, id);
                            return 1;
                        })))
        );
    }

    private static boolean valid(String id) {
        return id.equals("blacksmith") || id.equals("explorer") || id.equals("hunter");
    }
}
