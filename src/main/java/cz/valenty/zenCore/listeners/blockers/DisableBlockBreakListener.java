package cz.valenty.zenCore.listeners.blockers;

import cz.valenty.zenCore.ZenCore;
import cz.valenty.zenCore.utils.ChatInfo;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class DisableBlockBreakListener implements Listener{
    private final ChatInfo chatInfo;

    public DisableBlockBreakListener(ChatInfo chatInfo){
        this.chatInfo = chatInfo;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreakBlock(BlockBreakEvent evt){
        Player p = evt.getPlayer();
        World world = p.getWorld();

        for(String disabledWorld:ZenCore.getInstance().getDisabledBlockBreakWorlds()){
            if(disabledWorld.equalsIgnoreCase(world.getName())){
                chatInfo.sendMessage("general.cantBreakBlock",p);
                evt.setCancelled(true);
                break;
            }
        }
    }
}