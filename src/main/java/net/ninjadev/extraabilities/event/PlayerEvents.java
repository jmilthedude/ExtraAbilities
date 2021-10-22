package net.ninjadev.extraabilities.event;

import net.ninjadev.extraabilities.entity.AbilityPlayer;
import net.ninjadev.extraabilities.spells.Spell;
import net.ninjadev.extraabilities.world.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    @EventHandler
    public void onCastSpell(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) return;
        if (event.getItem() == null || event.getItem().getType() != Material.BLAZE_ROD) return;

        AbilityPlayer player = PlayerData.get().getPlayer(event.getPlayer());
        if (player.getMagicAbility().getCurrentSpell() == null || player.getMagicAbility().getLearnedSpells().isEmpty()) {
            return;
        }

        player.getMagicAbility().getCurrentSpell().cast(player);
        event.getPlayer().swingMainHand();
    }

    @EventHandler
    public void onChangeSpell(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        int newSlot = event.getNewSlot();
        int prevSlot = event.getPreviousSlot();
        ItemStack previous = player.getInventory().getItem(prevSlot);
        if (previous == null || previous.getType() != Material.BLAZE_ROD) return;
        if (player.isSneaking()) {
            boolean forward = getDirection(prevSlot, newSlot);
            event.setCancelled(true);
            AbilityPlayer abilityPlayer = PlayerData.get().getPlayer(player);
            Spell<?> spell = abilityPlayer.getMagicAbility().getNextSpell(forward);
            ItemMeta meta = previous.getItemMeta();
            if (meta == null) return;
            meta.setDisplayName("Spell: " + spell.getType().toString());
            previous.setItemMeta(meta);
        }
    }

    private boolean getDirection(int prevSlot, int newSlot) {
        if (prevSlot == 0 && newSlot == 8) return true;
        else if (prevSlot == 8 && newSlot == 0) return false;
        else return prevSlot > newSlot;
    }
}
