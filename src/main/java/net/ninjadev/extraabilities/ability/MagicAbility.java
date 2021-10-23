package net.ninjadev.extraabilities.ability;

import com.google.gson.annotations.Expose;
import net.ninjadev.extraabilities.spells.Spell;
import net.ninjadev.extraabilities.util.Cooldown;
import net.ninjadev.extraabilities.util.Tickable;
import net.ninjadev.extraabilities.world.data.PlayerData;

import java.util.ArrayList;
import java.util.List;

public class MagicAbility implements Tickable {

    @Expose private int current;
    @Expose private int max;
    @Expose private int regenRate;
    @Expose private int regenAmount;
    @Expose private int currentSpellIndex;
    @Expose private List<Spell.SpellType> learnedSpells = new ArrayList<>();

    private long totalTicks = 0;
    private Cooldown cooldown;

    public MagicAbility(int max) {
        this.current = max;
        this.max = max;
        this.regenRate = 15;
        this.regenAmount = 1;
    }

    @Override
    public void tick() {
        totalTicks++;
        if (getCooldown().isActive()) {
            getCooldown().update();
            return;
        }
        if (totalTicks % regenRate == 0) regen();
    }

    private void regen() {
        if (current == max) return;
        this.current = Math.min(max, current + regenAmount);
        PlayerData.get().markDirty();
    }

    public int getCurrentAmount() {
        return current;
    }

    public void decreaseAmount(int amount) {
        this.current = Math.max(0, current - amount);
        PlayerData.get().markDirty();
    }

    public void increaseAmount(int amount) {
        this.current = Math.min(max, current + amount);
        PlayerData.get().markDirty();
    }


    public int getMax() {
        return max;
    }

    public Spell<?> getCurrentSpell() {
        if (learnedSpells.isEmpty()) learnSpell(Spell.SpellType.HEAL);
        PlayerData.get().markDirty();
        return learnedSpells.get(currentSpellIndex).getSpell();
    }

    public Spell<?> getNextSpell(boolean forward) {
        if (forward) {
            this.currentSpellIndex = currentSpellIndex + 1 == learnedSpells.size() ? 0 : currentSpellIndex + 1;
        } else {
            this.currentSpellIndex = currentSpellIndex - 1 < 0 ? learnedSpells.size() - 1 : currentSpellIndex - 1;
        }
        return this.getCurrentSpell();
    }

    public List<Spell.SpellType> getLearnedSpells() {
        if (learnedSpells.isEmpty()) learnSpell(Spell.SpellType.HEAL);
        return learnedSpells;
    }

    public void learnSpell(Spell.SpellType type) {
        if (learnedSpells.contains(type)) return;

        this.learnedSpells.add(type);
        PlayerData.get().markDirty();
    }

    public void startCooldown(int ticks) {
        this.cooldown.set(ticks);
    }

    public Cooldown getCooldown() {
        if (this.cooldown == null) this.cooldown = new Cooldown();
        return cooldown;
    }
}
