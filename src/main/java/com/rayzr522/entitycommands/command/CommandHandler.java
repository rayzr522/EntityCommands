package com.rayzr522.entitycommands.command;

import com.rayzr522.entitycommands.EntityCommands;
import com.rayzr522.entitycommands.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import java.util.stream.Collectors;

/**
 * Created by Rayzr522 on 3/27/17.
 */
public class CommandHandler implements CommandExecutor {
    private final EntityCommands plugin;

    public CommandHandler(EntityCommands plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!checkPerms(sender, "EntityCommands.command", true)) {
            return true;
        }

        if (args.length < 1) {
            return usage(sender);
        }

        String sub = args[0].toLowerCase();
        if (sub.equals("reload")) {
            if (!checkPerms(sender, "EntityCommands.command.reload", true)) {
                return true;
            }

            plugin.reload();
            sender.sendMessage(plugin.tr("config.reloaded"));
            return true;
        } else if (sub.equals("info")) {
            if (!checkPerms(sender, "EntityCommands.command.info", true)) {
                return true;
            }

            plugin.getEntityManager()
                    .getEntityDataList()
                    .stream()
                    .map(data -> plugin.trRaw("command.info.template",
                            data.getKey(),
                            data.getCommand(),
                            data.getEntityTypes().stream()
                                    .map(EntityType::name)
                                    .map(TextUtils::prettyEnum)
                                    .collect(Collectors.joining(", ")),
                            data.getItemInfo().getDisplayString()
                    )).forEach(sender::sendMessage);

            return true;
        }

        return usage(sender);
    }

    private boolean checkPerms(CommandSender sender, String permission, boolean sendMessage) {
        if (!sender.hasPermission(permission)) {
            if (sendMessage) {
                sender.sendMessage(plugin.tr("no-permission", permission));
            }
            return false;
        }
        return true;
    }

    private boolean usage(CommandSender sender) {
        sender.sendMessage(plugin.trRaw("command.usage"));
        return true;
    }
}
