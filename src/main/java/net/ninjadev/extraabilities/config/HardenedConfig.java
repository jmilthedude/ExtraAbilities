package net.ninjadev.extraabilities.config;

import com.google.gson.annotations.Expose;
import net.ninjadev.extraabilities.ability.hardened.HardenedProperties;

public class HardenedConfig extends Config {

    @Expose private HardenedProperties baseProperties;

    @Override
    public String getName() {
        return "Hardened";
    }

    @Override
    protected void reset() {
        this.baseProperties = HardenedProperties.getDefault();
    }

    public HardenedProperties getBaseProperties() {
        return baseProperties;
    }
}
