package net.ninjadev.extraabilities.init;

import net.ninjadev.extraabilities.ExtraAbilities;
import net.ninjadev.extraabilities.event.PlayerEvents;
import net.ninjadev.extraabilities.event.WorldEvents;
import org.bukkit.event.Listener;

public class PluginEvents {

    public static void register() {
        registerEvent(new PlayerEvents());
        registerEvent(new WorldEvents());
    }

    private static void registerEvent(Listener event) {
        ExtraAbilities.getInstance().getServer().getPluginManager().registerEvents(event, ExtraAbilities.getInstance());
    }
}
