package com.rayzr522.entitycommands.config;

import com.google.common.collect.Lists;
import com.rayzr522.entitycommands.utils.ConfigUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Rayzr522 on 3/27/17.
 */
public class EntityData {
    private String key;
    private List<EntityType> entityTypes = Lists.newArrayList();
    private ItemInfo itemInfo;
    private String command;

    public EntityData(String key, List<EntityType> entityTypes, ItemInfo itemInfo, String command) {
        this.key = key;
        this.entityTypes = entityTypes;
        this.itemInfo = itemInfo;
        this.command = command;
    }

    /**
     *
     * @param key
     * @param config The {@link ConfigurationSection} to load the data from
     * @return The {@link EntityData} represented by the data
     */
    public static EntityData fromConfig(String key, ConfigurationSection config) {
        Validate.isTrue(config.contains("entities"), "EntityData is missing 'entities'!");
        Validate.isTrue(config.contains("command"), "EntityData is missing 'command'!");

        List<EntityType> entityTypes = ConfigUtils.getEnumList(EntityType.class, config, "entities");
        if (entityTypes == null || entityTypes.isEmpty()) {
            throw new IllegalArgumentException("No valid entity types were specified!");
        }
        ItemInfo itemInfo = new ItemInfo();
        if (config.isConfigurationSection("item")) {
            itemInfo = ItemInfo.fromConfig(config.getConfigurationSection("item"));
        }

        return new EntityData(key, entityTypes, itemInfo, config.getString("command"));
    }

    public String getKey() {
        return key;
    }

    public List<EntityType> getEntityTypes() {
        return entityTypes;
    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public String getCommand() {
        return command;
    }

    public void execute(Player player, Entity clicked) {
        boolean playerCommand = command.startsWith("p:");

        String consoleCommand = (playerCommand ? command.substring(2) : command)
                .replace("{user}", player.getName())
                .replace("{name}", clicked.getName());

        CommandSender sender = playerCommand ? player : Bukkit.getConsoleSender();

        Bukkit.dispatchCommand(sender, consoleCommand);
    }
}
