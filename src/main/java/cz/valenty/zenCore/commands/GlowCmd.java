package cz.valenty.zenCore.commands;

import cz.valenty.zenCore.ZenCore;
import cz.valenty.zenCore.utils.ChatInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlowCmd implements CommandExecutor{
    private ZenCore plugin;
    private final ChatInfo chatInfo;

    public GlowCmd(ZenCore plugin,ChatInfo chatInfo){
        this.plugin = plugin;
        this.chatInfo = chatInfo;
    }

    @Override
    public boolean onCommand(CommandSender s,Command cmd,String label,String[] args){
        Player p = (Player) s;

        if(!p.hasPermission("zencore.cmd.glow")){
            chatInfo.sendMessage("general.noPermission",p);
            return true;
        }

        boolean newState = !p.isGlowing();
        p.setGlowing(newState);

        String path = newState? "messages.glow.glowEnabled":"messages.glow.glowDisabled";
        chatInfo.sendMessage(path,p);

        return true;
    }
}