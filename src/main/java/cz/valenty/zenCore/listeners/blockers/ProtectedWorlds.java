package cz.valenty.zenCore.listeners.blockers;

import cz.valenty.zenCore.ZenCore;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ProtectedWorlds implements Listener{
    @EventHandler(ignoreCancelled = true)
    public void onEntitySpawn(EntitySpawnEvent evt){
        Entity entity = evt.getEntity();
        World bukkitWorld = entity.getWorld();

        ZenCore.getInstance().getProtectedWorlds().forEach(world -> {
            if(world.equalsIgnoreCase(bukkitWorld.getName())) evt.setCancelled(true);
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerFallDamage(EntityDamageEvent evt){
        if(evt.getEntity() instanceof Player){
            Player p = (Player) evt.getEntity();

            if(evt.getCause() == EntityDamageEvent.DamageCause.FALL) {
                World bukkitWorld = p.getWorld();

                ZenCore.getInstance().getProtectedWorlds().forEach(world -> {
                    if(world.equalsIgnoreCase(bukkitWorld.getName())) evt.setCancelled(true);
                });
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerFireDamage(EntityDamageEvent evt){
        if(evt.getEntity() instanceof Player){
            Player p = (Player) evt.getEntity();

            if(evt.getCause() == EntityDamageEvent.DamageCause.FIRE) {
                World bukkitWorld = p.getWorld();

                ZenCore.getInstance().getProtectedWorlds().forEach(world -> {
                    if(world.equalsIgnoreCase(bukkitWorld.getName())) evt.setCancelled(true);
                });
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerFreezeDamage(EntityDamageEvent evt){
        if(evt.getEntity() instanceof Player){
            Player p = (Player) evt.getEntity();

            if(evt.getCause() == EntityDamageEvent.DamageCause.FREEZE) {
                World bukkitWorld = p.getWorld();

                ZenCore.getInstance().getProtectedWorlds().forEach(world -> {
                    if(world.equalsIgnoreCase(bukkitWorld.getName())) evt.setCancelled(true);
                });
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDrownDamage(EntityDamageEvent evt){
        if(evt.getEntity() instanceof Player){
            Player p = (Player) evt.getEntity();

            if(evt.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                World bukkitWorld = p.getWorld();

                ZenCore.getInstance().getProtectedWorlds().forEach(world -> {
                    if(world.equalsIgnoreCase(bukkitWorld.getName())) evt.setCancelled(true);
                });
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerSuffoscationDamage(EntityDamageEvent evt){
        if(evt.getEntity() instanceof Player){
            Player p = (Player) evt.getEntity();

            if(evt.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                World bukkitWorld = p.getWorld();

                ZenCore.getInstance().getProtectedWorlds().forEach(world -> {
                    if(world.equalsIgnoreCase(bukkitWorld.getName())) evt.setCancelled(true);
                });
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerBlockExplosionDamage(EntityDamageEvent evt){
        if(evt.getEntity() instanceof Player){
            Player p = (Player) evt.getEntity();

            if(evt.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                World bukkitWorld = p.getWorld();

                ZenCore.getInstance().getProtectedWorlds().forEach(world -> {
                    if(world.equalsIgnoreCase(bukkitWorld.getName())) evt.setCancelled(true);
                });
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerAnyDamage(EntityDamageEvent evt){
        if(evt.getEntity() instanceof Player){
            Player p = (Player) evt.getEntity();

            if(evt.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || evt.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || evt.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK || evt.getCause() == EntityDamageEvent.DamageCause.CRAMMING || evt.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR || evt.getCause() == EntityDamageEvent.DamageCause.PROJECTILE || evt.getCause() == EntityDamageEvent.DamageCause.LAVA || evt.getCause() == EntityDamageEvent.DamageCause.MAGIC || evt.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK || evt.getCause() == EntityDamageEvent.DamageCause.WORLD_BORDER || evt.getCause() == EntityDamageEvent.DamageCause.VOID || evt.getCause() == EntityDamageEvent.DamageCause.CAMPFIRE || evt.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL || evt.getCause() == EntityDamageEvent.DamageCause.LIGHTNING || evt.getCause() == EntityDamageEvent.DamageCause.MELTING || evt.getCause() == EntityDamageEvent.DamageCause.POISON || evt.getCause() == EntityDamageEvent.DamageCause.KILL || evt.getCause() == EntityDamageEvent.DamageCause.SUICIDE || evt.getCause() == EntityDamageEvent.DamageCause.WITHER || evt.getCause() == EntityDamageEvent.DamageCause.THORNS || evt.getCause() == EntityDamageEvent.DamageCause.CONTACT || evt.getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH || evt.getCause() == EntityDamageEvent.DamageCause.DRYOUT || evt.getCause() == EntityDamageEvent.DamageCause.SONIC_BOOM || evt.getCause() == EntityDamageEvent.DamageCause.STARVATION || evt.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK){
                World bukkitWorld = p.getWorld();

                ZenCore.getInstance().getProtectedWorlds().forEach(world -> {
                    if(world.equalsIgnoreCase(bukkitWorld.getName())) evt.setCancelled(true);
                });
            }
        }
    }
}