package net.ninjadev.extraabilities.event;

import net.ninjadev.extraabilities.init.PluginData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

public class WorldEvents implements Listener {

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        PluginData.saveAll();
    }

}
