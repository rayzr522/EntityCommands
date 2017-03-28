package com.rayzr522.entitycommands.utils;

import org.bukkit.ChatColor;

/**
 * Created by Rayzr522 on 3/28/17.
 */
public class TextUtils {
    public static String colorize(String input) {
        return input == null ? null : ChatColor.translateAlternateColorCodes('&', input);
    }
}
