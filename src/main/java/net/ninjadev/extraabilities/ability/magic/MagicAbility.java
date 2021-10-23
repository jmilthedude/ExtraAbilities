package net.ninjadev.extraabilities.ability.magic;

import com.google.gson.annotations.Expose;
import net.ninjadev.extraabilities.spells.Spell;
import net.ninjadev.extraabilities.util.Cooldown;
import net.ninjadev.extraabilities.util.Saveable;
import net.ninjadev.extraabilities.util.Tickable;

import java.util.ArrayList;
import java.util.List;

public class MagicAbility implements Tickable, Saveable {

    @Expose private final MagicProperties stats;
    @Expose int currentSpellIndex;
    @Expose List<Spell.SpellType> learnedSpells = new ArrayList<>();

    private long totalTicks = 0;
    private Cooldown cooldown;

    public MagicAbility(MagicProperties stats) {
        this.stats = stats;
    }

    @Override
    public void tick() {
        totalTicks++;
        if (getCooldown().isActive()) {
            getCooldown().update();
            return;
        }
        if (totalTicks % stats.getRegenRate() == 0) stats.regen();
    }

    public MagicProperties getProperties() {
        return stats;
    }

    public int getCurrentSpellIndex() {
        return currentSpellIndex;
    }

    public void setCurrentSpellIndex(int currentSpellIndex) {
        this.currentSpellIndex = currentSpellIndex;
        setModified();
    }

    public Spell<?> getCurrentSpell() {
        return learnedSpells.get(currentSpellIndex).getSpell();
    }

    public Spell<?> getNextLearnedSpell(boolean forward) {
        if (forward) {
            this.currentSpellIndex = currentSpellIndex + 1 == learnedSpells.size() ? 0 : currentSpellIndex + 1;
        } else {
            this.currentSpellIndex = currentSpellIndex - 1 < 0 ? learnedSpells.size() - 1 : currentSpellIndex - 1;
        }
        setModified();
        return this.getCurrentSpell();
    }

    public List<Spell.SpellType> getLearnedSpells() {
        if (learnedSpells.isEmpty()) learnSpell(Spell.SpellType.HEAL);
        return learnedSpells;
    }

    public void learnSpell(Spell.SpellType type) {
        if (learnedSpells.contains(type)) return;

        this.learnedSpells.add(type);
        setModified();
    }

    public void startCooldown(int ticks) {
        this.cooldown.set(ticks);
    }

    public Cooldown getCooldown() {
        if (this.cooldown == null) this.cooldown = new Cooldown();
        return cooldown;
    }
}
