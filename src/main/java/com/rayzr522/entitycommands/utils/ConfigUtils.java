package com.rayzr522.entitycommands.utils;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Rayzr522 on 3/27/17.
 */
public class ConfigUtils {
    public static String enumFormat(String input) {
        return input.trim()
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "_");
    }

    private static <T extends Enum> T getEnum(Class<T> enumType, String input) {
        if (input == null) {
            return null;
        }
        try {
            return (T) Enum.valueOf(enumType, enumFormat(input));
        } catch (IllegalArgumentException | ClassCastException e) {
            return null;
        }
    }

    public static <T extends Enum> T getEnum(Class<T> enumType, ConfigurationSection section, String key) {
        if (!section.isString(key)) {
            return null;
        }
        return getEnum(enumType, section.getString(key));
    }

    public static <T extends Enum> List<T> getEnumList(Class<T> enumType, ConfigurationSection section, String key) {
        if (!section.isList(key)) {
            return null;
        }

        return section.getList(key).stream()
                .map(Objects::toString)
                .map(name -> getEnum(enumType, name))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
