package net.ninjadev.extraabilities.spells;

import net.ninjadev.extraabilities.ExtraAbilities;
import net.ninjadev.extraabilities.entity.AbilityPlayer;
import net.ninjadev.extraabilities.init.PluginConfigs;
import net.ninjadev.extraabilities.spells.properties.FlameSpellProperties;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class FlameSpell extends Spell<FlameSpellProperties> {

    public FlameSpell() {
        super(PluginConfigs.getSpellsConfig().getFlameSpellProperties());
    }

    @Override
    public SpellType getType() {
        return SpellType.FLAME;
    }

    @Override
    public Sound getSuccessSound() {
        return Sound.ENTITY_BLAZE_SHOOT;
    }

    @Override
    public Sound getFailSound() {
        return Sound.BLOCK_CANDLE_EXTINGUISH;
    }

    @Override
    public boolean doCast(AbilityPlayer player) {
        double damageAmount = this.getProperties().getDamage();
        Player caster = player.getBukkitPlayer();
        World world = caster.getWorld();
        Location location = caster.getEyeLocation();
        Vector direction = caster.getLocation().getDirection();
        world.spawn(location, SmallFireball.class, smallFireball -> this.setFireballProperties(smallFireball, direction, damageAmount));
        playSuccess(world, caster.getLocation());
        return true;
    }

    private void setFireballProperties(SmallFireball fireball, Vector direction, double damageAmount) {
        fireball.setDirection(direction);
        fireball.setVelocity(direction.multiply(2.5d));
        PersistentDataContainer container = fireball.getPersistentDataContainer();
        container.set(ExtraAbilities.getKey("flameDamage"), PersistentDataType.DOUBLE, damageAmount);
    }



    /* ----------------------- events ------------------- */

    @EventHandler
    public void onImpactEntity(ProjectileHitEvent event) {
        if (event.getEntity() instanceof SmallFireball fireball) {
            PersistentDataContainer container = fireball.getPersistentDataContainer();
            Double damage = container.get(ExtraAbilities.getKey("flameDamage"), PersistentDataType.DOUBLE);
            if (damage != null) {
                event.setCancelled(true);
                if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity target) {
                    target.damage(damage, (Entity) event.getEntity().getShooter());
                    fireball.remove();
                }
            }
        }
    }

    @EventHandler
    public void onFireIgnited(BlockIgniteEvent event) {
        if (event.getIgnitingEntity() instanceof SmallFireball fireball) {
            PersistentDataContainer container = fireball.getPersistentDataContainer();
            Double damage = container.get(ExtraAbilities.getKey("flameDamage"), PersistentDataType.DOUBLE);
            if (damage != null) {
                event.setCancelled(true);
            }
        }
    }
}
