package net.ninjadev.extraabilities.config;

import com.google.gson.annotations.Expose;
import net.ninjadev.extraabilities.ability.magic.MagicProperties;
import net.ninjadev.extraabilities.spells.properties.FlameSpellProperties;
import net.ninjadev.extraabilities.spells.properties.HealSpellProperties;

public class MagicConfig extends Config {

    @Expose private MagicProperties baseProperties;

    @Expose private HealSpellProperties healSpellProperties;
    @Expose private FlameSpellProperties flameSpellProperties;

    @Override
    protected String getName() {
        return "Magic";
    }

    @Override
    protected void reset() {
        baseProperties = MagicProperties.getDefault();

        healSpellProperties = new HealSpellProperties(4.0d);
        flameSpellProperties = new FlameSpellProperties(4.0d);
    }

    public HealSpellProperties getHealSpellProperties() {
        return healSpellProperties;
    }

    public FlameSpellProperties getFlameSpellProperties() {
        return flameSpellProperties;
    }

    public MagicProperties getBaseProperties() {
        return baseProperties;
    }
}
