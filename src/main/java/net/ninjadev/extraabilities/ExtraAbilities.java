package net.ninjadev.extraabilities;

import net.ninjadev.extraabilities.init.*;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExtraAbilities extends JavaPlugin {

    private static Plugin INSTANCE;


    @Override
    public void onEnable() {
        if (INSTANCE == null) INSTANCE = this;

        PluginData.register();
        PluginConfigs.register();
        PluginEvents.register();
        PluginSpells.register();
        PluginTasks.register();
    }

    @Override
    public void onDisable() {
        PluginData.saveAll();
    }

    public static Plugin getInstance() {
        return INSTANCE;
    }

    public static String getPluginName() {
        return ExtraAbilities.class.getSimpleName();
    }

    public static NamespacedKey getKey(String name) {
        return new NamespacedKey(ExtraAbilities.getInstance(), name);
    }
}
