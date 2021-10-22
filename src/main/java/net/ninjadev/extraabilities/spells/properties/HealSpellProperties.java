package net.ninjadev.extraabilities.spells.properties;

import com.google.gson.annotations.Expose;

public class HealSpellProperties extends SpellProperties {

    @Expose private double healAmount;

    public HealSpellProperties(double healAmount) {
        super(25, 15);
        this.healAmount = healAmount;
    }

    public double getHealAmount() {
        return healAmount;
    }
}
