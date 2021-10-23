package net.ninjadev.extraabilities.entity;

import com.google.gson.annotations.Expose;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.ninjadev.extraabilities.ability.hardened.HardenedAbility;
import net.ninjadev.extraabilities.ability.magic.MagicAbility;
import net.ninjadev.extraabilities.init.PluginConfigs;
import net.ninjadev.extraabilities.spells.Spell;
import net.ninjadev.extraabilities.util.Saveable;
import net.ninjadev.extraabilities.util.Tickable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AbilityPlayer implements Tickable, Saveable {

    @Expose private final UUID uuid;
    @Expose private MagicAbility magicAbility;
    @Expose private HardenedAbility hardenedAbility;

    public AbilityPlayer(UUID uuid) {
        this.uuid = uuid;
        initMagicAbility();
        initHardenedAbility();
        this.magicAbility.learnSpell(Spell.SpellType.HEAL);
        this.magicAbility.learnSpell(Spell.SpellType.FLAME);
    }

    @Override
    public void tick() {
        this.magicAbility.tick();

        this.getBukkitPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Magic: " + this.magicAbility.getProperties().getMp()));
    }

    public void login() {
    }

    public void logout() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public MagicAbility getMagicAbility() {
        return magicAbility;
    }

    public HardenedAbility getHardenedAbility() {
        return hardenedAbility;
    }

    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public void initMagicAbility() {
        this.magicAbility = new MagicAbility(PluginConfigs.getMagicConfig().getBaseProperties());
    }

    public void initHardenedAbility() {
        this.hardenedAbility = new HardenedAbility(PluginConfigs.getHardenedConfig().getBaseProperties());
    }
}
