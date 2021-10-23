package net.ninjadev.extraabilities.event;

import net.ninjadev.extraabilities.entity.AbilityPlayer;
import net.ninjadev.extraabilities.world.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        AbilityPlayer abilityPlayer = PlayerData.get().getPlayer(player);
        abilityPlayer.login();
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        AbilityPlayer abilityPlayer = PlayerData.get().getPlayer(player);
        abilityPlayer.login();
    }


}
