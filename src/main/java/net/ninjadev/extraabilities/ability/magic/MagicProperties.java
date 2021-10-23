package net.ninjadev.extraabilities.ability.magic;

import com.google.gson.annotations.Expose;
import net.ninjadev.extraabilities.util.Saveable;

public class MagicProperties implements Saveable {
    @Expose int mp;
    @Expose int maxMp;
    @Expose int regenRate;
    @Expose int regenAmount;

    public MagicProperties() {
    }

    void regen() {
        if (mp == maxMp) return;
        this.mp = Math.min(maxMp, mp + regenAmount);
        setModified();
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
        setModified();
    }

    public void decreaseMp(int amount) {
        this.mp = Math.max(0, mp - amount);
        setModified();
    }

    public void increaseMp(int amount) {
        this.mp = Math.min(maxMp, mp + amount);
        setModified();
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
        setModified();
    }

    public int getRegenRate() {
        return regenRate;
    }

    public void setRegenRate(int regenRate) {
        this.regenRate = regenRate;
        setModified();
    }

    public int getRegenAmount() {
        return regenAmount;
    }

    public void setRegenAmount(int regenAmount) {
        this.regenAmount = regenAmount;
        setModified();
    }

    public static MagicProperties getDefault() {
        MagicProperties stats = new MagicProperties();
        stats.setMp(100);
        stats.setMaxMp(100);
        stats.setRegenRate(10);
        stats.setRegenAmount(1);
        return stats;
    }
}