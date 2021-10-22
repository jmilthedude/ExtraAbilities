package net.ninjadev.extraabilities.init;

import net.ninjadev.extraabilities.ExtraAbilities;
import net.ninjadev.extraabilities.util.Logger;
import net.ninjadev.extraabilities.world.WorldTickTask;
import org.bukkit.Bukkit;

public class PluginTasks {
    public static void register() {
        registerTask(new WorldTickTask(), 0L, 1L);

        Logger.info("Tasks registered.");
    }

    private static int registerTask(Runnable task, long delay, long interval) {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(ExtraAbilities.getInstance(), task, delay, interval);
    }
}
