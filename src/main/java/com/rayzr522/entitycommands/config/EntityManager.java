package com.rayzr522.entitycommands.config;

import com.google.common.collect.Lists;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Created by Rayzr522 on 3/27/17.
 */
public class EntityManager {
    private List<EntityData> entityDataList = Lists.newArrayList();
    private Plugin plugin;

    public EntityManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void load(ConfigurationSection config) {
        Objects.requireNonNull(config, "config cannot be null!");

        for (String key : config.getKeys(false)) {
            try {
                entityDataList.add(EntityData.fromConfig(key, config.getConfigurationSection(key)));
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to load entity data: '" + key + "'", e);
            }
        }
    }

    public List<EntityData> getApplicable(EntityType type) {
        return entityDataList.stream()
                .filter(entityData -> entityData.getEntityTypes().contains(type))
                .collect(Collectors.toList());
    }

    public List<EntityData> getEntityDataList() {
        return entityDataList;
    }
}
