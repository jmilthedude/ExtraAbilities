package net.ninjadev.extraabilities.ability.hardened;

import com.google.gson.annotations.Expose;
import net.ninjadev.extraabilities.util.Saveable;

public class HardenedProperties implements Saveable {

    @Expose private double damageReduction;

    public HardenedProperties(double damageReduction) {
        this.damageReduction = damageReduction;
    }

    public double getDamageReduction() {
        return damageReduction;
    }

    public void setDamageReduction(double damageReduction) {
        this.damageReduction = damageReduction;
        setModified();
    }

    public static HardenedProperties getDefault() {
        return new HardenedProperties(0.0d);
    }
}
