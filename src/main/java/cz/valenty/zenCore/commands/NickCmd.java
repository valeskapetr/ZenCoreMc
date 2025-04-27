package cz.valenty.zenCore.commands;

import cz.valenty.zenCore.ZenCore;
import cz.valenty.zenCore.utils.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NickCmd implements CommandExecutor,TabCompleter{
    private ZenCore plugin;
    private final ChatInfo chatInfo;

    public NickCmd(ZenCore plugin,ChatInfo chatInfo){
        this.plugin = plugin;
        this.chatInfo = chatInfo;
    }

    @Override
    public boolean onCommand(CommandSender s,Command cmd,String label,String[] args){
        if(args.length == 0){
            chatInfo.sendMessage("messages.nick.usage",s);
            return true;
        }

        String arg = args[0];
        Player t;

        if(args.length >= 2){
            if(!s.hasPermission("zencore.cmd.nickOthers")) {
                chatInfo.sendMessage("general.noPermission",s);
                return true;
            }

            t = Bukkit.getPlayer(args[1]);

            if(t == null){
                chatInfo.prepareMessage("general.playerNotFound").replace("player",t.getName()).sendTo(s);
                return true;
            }
        }else{
            t = (Player) s;

            if(!s.hasPermission("zencore.cmd.nickSelf")) {
                chatInfo.sendMessage("general.noPermission",s);
                return true;
            }
        }

        if(arg.equalsIgnoreCase("reset")){
            t.setDisplayName(t.getName());
            t.setPlayerListName(t.getName());

            chatInfo.sendMessage("messages.nick.nickReset",s);

            if(!s.equals(t)) chatInfo.sendMessage("messages.nick.resetTarget",t);

            return true;
        }

        String newNick = ChatColor.translateAlternateColorCodes('&',arg);
        t.setDisplayName(newNick);
        t.setPlayerListName(newNick);
        chatInfo.prepareMessage("messages.nick.nickSet").replace("nick",newNick).sendTo(s);

        if(!s.equals(t)) chatInfo.sendMessage("messages.nick.changedTarget",t);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s,Command cmd,String label,String[] args){
        List<String> suggestions = new ArrayList<>();

        if(args.length == 1){
            suggestions.add("reset");

            for(Player online:Bukkit.getOnlinePlayers()) suggestions.add(online.getName());
        }else if(args.length == 2 && s.hasPermission("zencore.cmd.nickOthers")){
            String partial = args[1].toLowerCase();

            for(Player online:Bukkit.getOnlinePlayers()) if(online.getName().toLowerCase().startsWith(partial)) suggestions.add(online.getName());
        }

        return suggestions;
    }
}