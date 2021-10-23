package net.ninjadev.extraabilities.util;

import net.ninjadev.extraabilities.world.data.PlayerData;

public interface Saveable {

    default void setModified() {
        PlayerData.get().markDirty();
    }
}
