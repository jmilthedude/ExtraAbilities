package net.ninjadev.extraabilities.world.data;

import com.google.gson.annotations.Expose;
import net.ninjadev.extraabilities.entity.AbilityPlayer;
import net.ninjadev.extraabilities.init.PluginData;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class PlayerData extends Data {

    @Expose private final HashMap<UUID, AbilityPlayer> players = new HashMap<>();

    @Override
    public String getName() {
        return "PlayerData";
    }

    @Override
    public void tick() {
        players.values().stream().filter(player -> player.getBukkitPlayer() != null && player.getBukkitPlayer().isOnline()).forEach(AbilityPlayer::tick);
    }

    public AbilityPlayer getPlayer(Player player) {
        return this.getPlayer(player.getUniqueId());
    }

    private AbilityPlayer getPlayer(UUID uuid) {
        AbilityPlayer survivor = players.computeIfAbsent(uuid, AbilityPlayer::new);
        markDirty();
        return survivor;
    }

    public Collection<AbilityPlayer> getPlayers() {
        return this.players.values();
    }

    public static PlayerData get() {
        return PluginData.PLAYER_DATA;
    }

}
