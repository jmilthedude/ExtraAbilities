package net.ninjadev.extraabilities.init;

import net.ninjadev.extraabilities.config.SpellsConfig;
import net.ninjadev.extraabilities.util.Logger;

public class PluginConfigs {

    private static SpellsConfig SPELLS;

    public static void register() {
        SPELLS = new SpellsConfig().read();

        Logger.info("Configs registered.");
    }

    public static SpellsConfig getSpellsConfig() {
        return SPELLS;
    }

}
