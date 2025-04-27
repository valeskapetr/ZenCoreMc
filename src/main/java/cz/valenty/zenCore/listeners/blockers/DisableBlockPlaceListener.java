package cz.valenty.zenCore.listeners.blockers;

import cz.valenty.zenCore.ZenCore;
import cz.valenty.zenCore.utils.ChatInfo;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class DisableBlockPlaceListener implements Listener{
    private final ChatInfo chatInfo;

    public DisableBlockPlaceListener(ChatInfo chatInfo){
        this.chatInfo = chatInfo;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent evt){
        World bukkitWorld = evt.getPlayer().getWorld();
        Player p = evt.getPlayer();

        ZenCore.getInstance().getDisabledBlockPlaceWorlds().forEach(world -> {
            if(world.equalsIgnoreCase(bukkitWorld.getName())){
                chatInfo.sendMessage("general.cantPlaceBlock",p);
                evt.setCancelled(true);
            }
        });
    }
}