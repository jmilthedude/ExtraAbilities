package net.ninjadev.extraabilities.spells;

import net.ninjadev.extraabilities.entity.AbilityPlayer;
import net.ninjadev.extraabilities.init.PluginSpells;
import net.ninjadev.extraabilities.spells.properties.SpellProperties;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.Listener;

public abstract class Spell<P extends SpellProperties> implements Listener {

    private final P properties;

    public Spell(P properties) {
        this.properties = properties;
    }

    protected abstract boolean doCast(AbilityPlayer player);

    protected abstract Sound getSuccessSound();

    protected abstract Sound getFailSound();

    public abstract Spell.SpellType getType();

    protected boolean canCast(AbilityPlayer player) {
        if (player.getMagicAbility().getCooldown().isActive()) return false;
        return player.getMagicAbility().getCurrentAmount() - properties.getCost() >= 0;
    }

    public void cast(AbilityPlayer player) {
        if (!canCast(player)) {
            playFail(player.getBukkitPlayer().getWorld(), player.getBukkitPlayer().getLocation());
            return;
        }

        if (doCast(player)) {
            player.getMagicAbility().decreaseAmount(this.getProperties().getCost());
            player.getMagicAbility().startCooldown(this.getProperties().getCooldown());
        }
    }

    public P getProperties() {
        return this.properties;
    }

    public void playFail(World world, Location location) {
        world.playSound(location, getFailSound(), .8f, 1.0f);
    }

    public void playSuccess(World world, Location location) {
        world.playSound(location, getSuccessSound(), .8f, 1.5f);
    }


    public enum SpellType {
        HEAL(ChatColor.RED),
        FLAME(ChatColor.GOLD);

        ChatColor color;

        SpellType(ChatColor color) {
            this.color = color;
        }

        public ChatColor getColor() {
            return color;
        }

        public Spell<?> getSpell() {
            return PluginSpells.getSpell(this);
        }

        @Override
        public String toString() {
            return this.getColor() + this.name();
        }
    }
}
