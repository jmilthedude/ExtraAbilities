package net.ninjadev.extraabilities.config;

import com.google.gson.annotations.Expose;
import net.ninjadev.extraabilities.ExtraAbilities;
import net.ninjadev.extraabilities.spells.properties.FlameSpellProperties;
import net.ninjadev.extraabilities.spells.properties.HealSpellProperties;

import java.io.File;

public class SpellsConfig extends Config {

    @Expose private HealSpellProperties healSpellProperties;
    @Expose private FlameSpellProperties flameSpellProperties;

    @Override
    protected String getName() {
        return "spells";
    }

    @Override
    protected void reset() {
        healSpellProperties = new HealSpellProperties(4.0d);
        flameSpellProperties = new FlameSpellProperties(4.0d);
    }

    @Override
    protected File getRoot() {
        return new File(ExtraAbilities.getInstance().getDataFolder(), "config/");
    }

    public HealSpellProperties getHealSpellProperties() {
        return healSpellProperties;
    }

    public FlameSpellProperties getFlameSpellProperties() {
        return flameSpellProperties;
    }
}
