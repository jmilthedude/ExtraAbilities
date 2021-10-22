package net.ninjadev.extraabilities.spells;

import com.google.gson.annotations.Expose;
import net.ninjadev.extraabilities.entity.AbilityPlayer;
import net.ninjadev.extraabilities.init.PluginSpells;
import net.ninjadev.extraabilities.spells.properties.SpellProperties;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.Listener;

public class Spell<P extends SpellProperties> implements ICastable, Listener {

    @Expose private final SpellType type;
    @Expose private final P properties;

    public Spell(SpellType type, P properties) {
        this.type = type;
        this.properties = properties;
    }

    private boolean canCast(AbilityPlayer player) {
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

    public SpellType getType() {
        return type;
    }

    public void playFail(World world, Location location) {
        world.playSound(location, getFailSound(), .8f, 1.0f);
    }

    public void playSuccess(World world, Location location) {
        world.playSound(location, getSuccessSound(), .8f, 1.5f);
    }

    @Override
    public boolean doCast(AbilityPlayer player) {
        return false;
    }

    @Override
    public Sound getSuccessSound() {
        return null;
    }

    @Override
    public Sound getFailSound() {
        return null;
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
            return this.color + this.name();
        }
    }
}
