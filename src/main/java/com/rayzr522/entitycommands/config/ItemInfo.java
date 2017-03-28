package com.rayzr522.entitycommands.config;

import com.rayzr522.entitycommands.utils.ConfigUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

import static com.rayzr522.entitycommands.utils.TextUtils.colorize;

/**
 * Created by Rayzr522 on 3/28/17.
 */
public class ItemInfo {
    private static short NULL_SHORT = -1;

    private Material material = null;
    private short data = NULL_SHORT;
    private String name = null;

    private ItemInfo() {
        // Empty for use with #fromConfig
    }

    public static ItemInfo fromConfig(ConfigurationSection config) {
        ItemInfo itemInfo = new ItemInfo();

        // If the key doesn't exist, it'll just return null. That's fine, we're
        // using null to indicate no material requirement anyways.
        itemInfo.material = ConfigUtils.getEnum(Material.class, config, "material");

        if (config.isInt("data")) {
            int data = config.getInt("data");
            if (data >= 0 && data < Short.MAX_VALUE) {
                itemInfo.data = (short) data;
            }
        }

        itemInfo.name = colorize(config.getString("name"));

        return itemInfo;
    }

    /**
     * @param item The {@link ItemStack} to compare
     * @return Whether or not the {@link ItemStack} matches
     */
    public boolean matches(ItemStack item) {
        if (item == null) {
            return false;
        }

        if (material != null && item.getType() != material) {
            return false;
        }

        if (data != NULL_SHORT && item.getDurability() != data) {
            return false;
        }

        if (name != null) {
            if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
                return false;
            }
            if (!item.getItemMeta().getDisplayName().equals(name)) {
                return false;
            }
        }

        return true;
    }

    public Optional<Material> getMaterial() {
        return Optional.ofNullable(material);
    }

    public Optional<Short> getData() {
        return data == NULL_SHORT ? Optional.empty() : Optional.of(data);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}
