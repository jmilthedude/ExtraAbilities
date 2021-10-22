package net.ninjadev.extraabilities.spells.properties;

import com.google.gson.annotations.Expose;

public class FlameSpellProperties extends SpellProperties {
    @Expose private final double damage;

    public FlameSpellProperties(double damage) {
        super(10, 10);
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }
}
