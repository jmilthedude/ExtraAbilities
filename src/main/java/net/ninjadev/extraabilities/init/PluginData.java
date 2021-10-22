package net.ninjadev.extraabilities.init;

import net.ninjadev.extraabilities.world.data.PlayerData;

public class PluginData {

    public static PlayerData PLAYER_DATA;

    public static void register() {
        PLAYER_DATA = (PlayerData) new PlayerData().read();
    }

    public static void saveAll() {
        PLAYER_DATA.save();
    }
}
