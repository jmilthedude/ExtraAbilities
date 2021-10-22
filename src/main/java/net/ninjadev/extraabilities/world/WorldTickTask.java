package net.ninjadev.extraabilities.world;

import net.ninjadev.extraabilities.world.data.PlayerData;

public class WorldTickTask implements Runnable {
    @Override
    public void run() {
        PlayerData.get().tick();
    }
}
