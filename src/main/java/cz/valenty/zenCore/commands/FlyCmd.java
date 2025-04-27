package cz.valenty.zenCore.commands;

import cz.valenty.zenCore.ZenCore;
import cz.valenty.zenCore.utils.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/*
 * @name Fly Command
 * @description Command for players flying.
 */
public class FlyCmd implements CommandExecutor,TabCompleter{
    private ZenCore plugin;
    private final ChatInfo chatInfo;

    public FlyCmd(ZenCore plugin, ChatInfo chatInfo){
        this.plugin = plugin;
        this.chatInfo = chatInfo;
    }

    @Override
    public boolean onCommand(CommandSender s,Command cmd,String label,String[] args){
        if(args.length == 0){
            if(!(s instanceof Player)){
                s.sendMessage("Tento příkaz může použít jen hráč.");
                return true;
            }

            Player p = (Player) s;

            if(!p.hasPermission("zencore.cmd.flySelf")){
                chatInfo.sendMessage("general.noPermission",p);
                return true;
            }

            boolean newState = !p.getAllowFlight();
            p.setAllowFlight(newState);
            p.setFlying(newState);

            String path = newState? "messages.fly.flyEnabled":"messages.fly.flyDisabled";
            chatInfo.sendMessage(path,p);

            return true;
        }

        if(!s.hasPermission("zencore.cmd.flyOthers")){
            chatInfo.sendMessage("general.noPermission",s);
            return true;
        }

        Player t = Bukkit.getPlayer(args[0]);

        if(t == null || !t.isOnline()){
            chatInfo.sendMessage("general.playerNotFound",s);
            return true;
        }

        boolean newState = !t.getAllowFlight();
        t.setAllowFlight(newState);
        t.setFlying(newState);

        String pathSender = newState? "messages.fly.flyEnabledOther":"messages.fly.flyDisabledOther";
        String pathTarget = newState? "messages.fly.flyEnabledByOther":"messages.fly.flyDisabledByOther";

        chatInfo.sendMessage(pathSender,s,"player",t.getName());
        chatInfo.sendMessage(pathTarget,t);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s,Command cmd,String label,String[] args){
        if(args.length == 1 && s.hasPermission("zencore.cmd.flyOthers")){
            List<String> suggestions = new ArrayList<>();
            String partial = args[0].toLowerCase();

            for(Player online:Bukkit.getOnlinePlayers()){
                String name = online.getName();
                if(name.toLowerCase().startsWith(partial)) suggestions.add(name);
            }

            return suggestions;
        }

        return new ArrayList<>();
    }
}