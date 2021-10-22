package net.ninjadev.extraabilities.spells;

import net.ninjadev.extraabilities.entity.AbilityPlayer;
import org.bukkit.Sound;

public interface ICastable {

    boolean doCast(AbilityPlayer player);

    Sound getSuccessSound();

    Sound getFailSound();
}
