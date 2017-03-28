package com.rayzr522.entitycommands;

import com.rayzr522.entitycommands.command.CommandHandler;
import com.rayzr522.entitycommands.config.EntityManager;
import com.rayzr522.entitycommands.config.Language;
import com.rayzr522.entitycommands.event.EventListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * @author Rayzr
 */
public class EntityCommands extends JavaPlugin {
    private static EntityCommands instance;

    private Language lang = new Language();
    private EntityManager entityManager = new EntityManager(this);

    @Override
    public void onEnable() {
        instance = this;

        getCommand("entitycommands").setExecutor(new CommandHandler(this));
        getServer().getPluginManager().registerEvents(new EventListener(this), this);

        reload();
    }
    
    @Override
    public void onDisable() {
        instance = null;
    }
    
    /**
     * (Re)loads all configs fromConfig the disk
     */
    public void reload() {
        saveDefaultConfig();
        reloadConfig();

        lang.load(getConfig("messages.yml"));
        entityManager.load(getConfig().getConfigurationSection("commands"));
    }

    /**
     * If the file is not found and there is a default file in the JAR, it saves the default file to the plugin data folder first
     * 
     * @param path The path to the config file (relative to the plugin data folder)
     * @return The {@link YamlConfiguration}
     */
    public YamlConfiguration getConfig(String path) {
        if (!getFile(path).exists() && getResource(path) != null) {
            saveResource(path, true);
        }
        return YamlConfiguration.loadConfiguration(getFile(path));
    }
    
    /**
     * Attempts to save a {@link YamlConfiguration} to the disk, and any {@link IOException}s are printed to the console
     * 
     * @param config The config to save
     * @param path The path to save the config file to (relative to the plugin data folder)
     */
    public void saveConfig(YamlConfiguration config, String path) {
        try {
            config.save(getFile(path));
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to save config", e);
        }
    }

    /**
     * @param path The path of the file (relative to the plugin data folder)
     * @return The {@link File}
     */
    public File getFile(String path) {
        return new File(getDataFolder(), path.replace('/', File.pathSeparatorChar));
    }
    
    /**
     * Returns a message fromConfig the language file
     * 
     * @param key The key of the message to translate
     * @param objects The formatting objects to use
     * @return The formatted message
     */
    public String tr(String key, Object... objects) {
        return lang.tr(key, objects);
    }

    /**
     * Returns a message fromConfig the language file without adding the prefix
     * 
     * @param key The key of the message to translate
     * @param objects The formatting objects to use
     * @return The formatted message
     */
    public String trRaw(String key, Object... objects) {
        return lang.trRaw(key, objects);
    }

    /**
     * @return The {@link Language} instance for this plugin
     */
    public Language getLang() {
        return lang;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public static EntityCommands getInstance() {
        return instance;
    }

}
