package net.ninjadev.extraabilities.spells;

import net.ninjadev.extraabilities.entity.AbilityPlayer;
import net.ninjadev.extraabilities.init.PluginConfigs;
import net.ninjadev.extraabilities.spells.properties.HealSpellProperties;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealSpell extends Spell<HealSpellProperties> {

    public HealSpell() {
        super(SpellType.HEAL, PluginConfigs.getSpellsConfig().getHealSpellProperties());
    }

    @Override
    public boolean doCast(@NotNull AbilityPlayer player) {
        double healAmount = this.getProperties().getHealAmount();

        Player target = player.getBukkitPlayer();

        AttributeInstance maxHealthAttribute = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealthAttribute == null) return false;
        double maxHealth = maxHealthAttribute.getValue();

        return attemptHeal(target, healAmount, maxHealth);
    }

    private boolean attemptHeal(Player target, double healAmount, double maxHealth) {
        if (target.getHealth() != maxHealth) {
            target.setHealth(Math.min(target.getHealth() + healAmount, maxHealth));
            playSuccess(target.getWorld(), target.getLocation());
            return true;
        }

        playFail(target.getWorld(), target.getLocation());
        return false;

    }

    @Override
    public Sound getSuccessSound() {
        return Sound.ENTITY_PLAYER_LEVELUP;
    }

    @Override
    public Sound getFailSound() {
        return Sound.BLOCK_CANDLE_EXTINGUISH;
    }
}
