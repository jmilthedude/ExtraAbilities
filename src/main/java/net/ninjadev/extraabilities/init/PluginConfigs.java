package net.ninjadev.extraabilities.init;

import net.ninjadev.extraabilities.config.HardenedConfig;
import net.ninjadev.extraabilities.config.MagicConfig;
import net.ninjadev.extraabilities.util.Logger;

public class PluginConfigs {

    private static MagicConfig magic;
    private static HardenedConfig hardened;

    public static void register() {
        magic = new MagicConfig().read();
        hardened = new HardenedConfig().read();

        Logger.info("Configs registered.");
    }

    public static MagicConfig getMagicConfig() {
        return magic;
    }

    public static HardenedConfig getHardenedConfig() {
        return hardened;
    }
}
