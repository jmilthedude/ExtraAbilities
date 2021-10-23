package net.ninjadev.extraabilities.entity;

import com.google.gson.annotations.Expose;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.ninjadev.extraabilities.ability.MagicAbility;
import net.ninjadev.extraabilities.spells.Spell;
import net.ninjadev.extraabilities.util.Tickable;
import net.ninjadev.extraabilities.world.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AbilityPlayer implements Tickable {

    @Expose private final UUID uuid;
    @Expose private MagicAbility magicAbility;

    public AbilityPlayer(UUID uuid) {
        this.uuid = uuid;
        this.magicAbility = new MagicAbility(100);
    }

    @Override
    public void tick() {
        this.magicAbility.tick();

        this.getBukkitPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Magic: " + this.magicAbility.getCurrentAmount()));
    }

    public void login() {
        this.magicAbility.learnSpell(Spell.SpellType.HEAL);
        this.magicAbility.learnSpell(Spell.SpellType.FLAME);
    }

    public void logout() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public MagicAbility getMagicAbility() {
        return magicAbility;
    }

    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public void markDirty() {
        PlayerData.get().markDirty();
    }


}
