package net.ninjadev.extraabilities.ability.hardened;

import com.google.gson.annotations.Expose;
import net.ninjadev.extraabilities.util.Saveable;

public class HardenedAbility implements Saveable {

    @Expose private HardenedProperties properties;

    public HardenedAbility(HardenedProperties properties) {
        this.properties = properties;
    }

    public HardenedProperties getProperties() {
        return properties;
    }

    public double getReducedDamage(double damage) {
        return damage - (damage * this.getProperties().getDamageReduction());
    }
}
