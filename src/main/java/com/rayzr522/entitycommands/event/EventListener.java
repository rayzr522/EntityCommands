package com.rayzr522.entitycommands.event;

import com.rayzr522.entitycommands.EntityCommands;
import com.rayzr522.entitycommands.config.EntityData;
import com.rayzr522.entitycommands.config.EntityManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Rayzr522 on 3/28/17.
 */
public class EventListener implements Listener {
    private EntityCommands plugin;

    public EventListener(EntityCommands plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (entity == null) {
            return;
        }

        EntityManager manager = plugin.getEntityManager();
        List<EntityData> applicable = manager.getApplicable(entity.getType());

        @SuppressWarnings("deprecated")
        ItemStack handItem = e.getPlayer().getItemInHand();
        applicable.stream()
                .filter(data -> data.getItemInfo().matches(handItem))
                .forEach(data -> data.execute(e.getPlayer(), entity));
    }
}
