package net.ninjadev.extraabilities.init;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.ninjadev.extraabilities.ExtraAbilities;
import net.ninjadev.extraabilities.spells.FlameSpell;
import net.ninjadev.extraabilities.spells.HealSpell;
import net.ninjadev.extraabilities.spells.Spell;
import org.jetbrains.annotations.Nullable;

public class PluginSpells {

    private static final BiMap<Spell.SpellType, Spell<?>> spellRegistry = HashBiMap.create();


    public static void register() {
        register(new HealSpell());
        register(new FlameSpell());
    }

    public static <S extends Spell<?>> void register(S spell) {
        spellRegistry.put(spell.getType(), spell);
        ExtraAbilities.getInstance().getServer().getPluginManager().registerEvents(spell, ExtraAbilities.getInstance());
    }

    @Nullable
    public static Spell<?> getSpell(Spell.SpellType type) {
        return spellRegistry.get(type);
    }

    public static Spell.SpellType getKey(Spell<?> spell) {
        return spellRegistry.inverse().get(spell);
    }

}
