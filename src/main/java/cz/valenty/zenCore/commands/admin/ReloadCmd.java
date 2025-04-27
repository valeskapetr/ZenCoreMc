package cz.valenty.zenCore.commands.admin;

import cz.valenty.zenCore.ZenCore;
import cz.valenty.zenCore.utils.ChatInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCmd implements CommandExecutor{
    private ZenCore plugin;
    private final ChatInfo chatInfo;

    public ReloadCmd(ZenCore plugin,ChatInfo chatInfo){
        this.plugin = plugin;
        this.chatInfo = chatInfo;
    }

    @Override
    public boolean onCommand(CommandSender s,Command cmd,String label,String[] args){
        if(!s.hasPermission("zencore.admin.reload")){
            chatInfo.sendMessage("general.noPermission",s);
            return true;
        }

        Player p = (Player) s;

        plugin.reload();
        chatInfo.sendMessage("general.reloaded",p);

        return true;
    }
}