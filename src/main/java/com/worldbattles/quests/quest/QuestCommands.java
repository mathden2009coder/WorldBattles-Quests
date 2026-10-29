package com.worldbattles.quests.quest;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class QuestCommands {
    @SubscribeEvent
    public void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("wbquests")
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
