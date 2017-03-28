package com.rayzr522.entitycommands.utils;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Rayzr522 on 3/28/17.
 */
public class TextUtils {
    public static String colorize(String input) {
        return input == null ? null : ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String prettyEnum(String input) {
        return Arrays.stream(input.split("_"))
                .map(str -> Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
