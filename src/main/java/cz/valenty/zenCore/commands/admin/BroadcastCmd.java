package cz.valenty.zenCore.commands.admin;

import cz.valenty.zenCore.ZenCore;
import cz.valenty.zenCore.utils.ChatInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCmd implements CommandExecutor{
    private ZenCore plugin;
    private final ChatInfo chatInfo;

    public BroadcastCmd(ZenCore plugin,ChatInfo chatInfo){
        this.plugin = plugin;
        this.chatInfo = chatInfo;
    }

    @Override
    public boolean onCommand(CommandSender s,Command cmd,String label,String[] args){
        if(!s.hasPermission("zencore.admin.broadcast")){
            chatInfo.sendMessage("general.noPermission",s);
            return true;
        }

        if(!(s instanceof Player)){
            s.sendMessage("This command can be used only in-game.");
            return true;
        }

        Player p = (Player) s;

        if(args.length == 0){
            chatInfo.sendMessage("messages.broadcast.basicUsage",p);
            return true;
        }

        String message = String.join(" ", args);
        chatInfo.prepareRaw("messages.broadcast.format").replace("message",message).sendTo(plugin.getServer());
        chatInfo.sendMessage("messages.broadcast.broadcastSent",p);

        return true;
    }
}