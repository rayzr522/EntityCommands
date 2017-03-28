package com.rayzr522.entitycommands.command;

import com.rayzr522.entitycommands.EntityCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
        if (!sender.hasPermission("EntityCommands.command")) {
            sender.sendMessage(plugin.tr("no-permission", "EntityCommands.command"));
            return true;
        }

        if (args.length < 1) {
            return usage(sender);
        }

        String sub = args[0].toLowerCase();
        if (sub.equals("reload")) {
            plugin.reload();
            sender.sendMessage(plugin.tr("config.reloaded"));
            return true;
        }

        return usage(sender);
    }

    private boolean usage(CommandSender sender) {
        sender.sendMessage(plugin.tr("usage"));
        return true;
    }
}
