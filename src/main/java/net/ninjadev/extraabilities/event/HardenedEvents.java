package net.ninjadev.extraabilities.event;

import net.ninjadev.extraabilities.entity.AbilityPlayer;
import net.ninjadev.extraabilities.util.Logger;
import net.ninjadev.extraabilities.world.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class HardenedEvents implements Listener {

    @EventHandler
    public void onPlayerReceiveDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        AbilityPlayer abilityPlayer = PlayerData.get().getPlayer(player);
        Logger.info("Initial Damage: " + event.getFinalDamage());
        double damage = abilityPlayer.getHardenedAbility().getReducedDamage(event.getFinalDamage());
        event.setDamage(damage);
        Logger.info("Reduced Damage: " + event.getFinalDamage());
    }
}
