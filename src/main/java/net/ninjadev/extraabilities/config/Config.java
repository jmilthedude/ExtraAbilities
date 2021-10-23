package net.ninjadev.extraabilities.config;

import net.ninjadev.extraabilities.world.data.Data;

public abstract class Config extends Data {

    @Override
    public boolean isDirty() {
        return true;
    }

    @Override
    public void tick() {}

    @Override
    protected String getDirectory() {
        return "config";
    }
}
