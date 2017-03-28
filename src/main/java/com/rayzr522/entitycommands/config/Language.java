package com.rayzr522.entitycommands.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Language {
    private Map<String, String> messages = new HashMap<>();
    private String prefix;

    private String toString(Object object) {
        if (object instanceof List) {
            return ((List<?>) object).stream().map(Objects::toString).collect(Collectors.joining("\n"));
        }
        return object.toString();
    }

    /**
     * @param config The {@link ConfigurationSection} to load the messages fromConfig
     */
    public void load(ConfigurationSection config) {
        messages.clear();
        for (String key :
                config.getKeys(true)) {
            if ("prefix".equals(key)) {
                this.prefix = ChatColor.translateAlternateColorCodes('&', toString(config.get(key)));
                continue;
            }
            messages.put(key, toString(config.get(key)));
        }
    }

    /**
     * @param key The key of the message to get
     * @param args The arguments to use for positional placeholders
     * @return The message (without the prefix)
     */
    public String trRaw(String key, Object... args) {
        Objects.requireNonNull(key, "key must not be null!");
        if (!messages.containsKey(key)) {
            return key;
        }

        String output = messages.get(key);

        for (int i = 0; i < args.length; i++) {
            output = output.replace(String.format("{%d}", i), args[i] == null ? "null" : args[i].toString());
        }

        return ChatColor.translateAlternateColorCodes('&', output);
    }

    /**
     * @param key The key of the message to get
     * @param args The arguments to use for positional placeholders
     * @return The message (with the prefix)
     *
     * @see #trRaw(String, Object...)
     */
    public String tr(String key, Object... args) {
        return prefix + trRaw(key, args).replace("\n", prefix);
    }

}
