package net.ninjadev.extraabilities.spells.properties;

import com.google.gson.annotations.Expose;

public class SpellProperties {

    @Expose private int cost;
    @Expose private int cooldown;

    public SpellProperties(int cost, int cooldown) {
        this.cost = cost;
        this.cooldown = cooldown;
    }

    public int getCost() {
        return cost;
    }

    public int getCooldown() {
        return cooldown;
    }
}
