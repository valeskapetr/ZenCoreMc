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
 * @name Heal Command
 * @description Command for healing players.
*/
public class HealCmd implements CommandExecutor,TabCompleter{
    private ZenCore plugin;
    private final ChatInfo chatInfo;

    public HealCmd(ZenCore plugin,ChatInfo chatInfo){
        this.plugin = plugin;
        this.chatInfo = chatInfo;
    }

    @Override
    public boolean onCommand(CommandSender s,Command cmd,String label,String[] args){
        if(args.length == 0){
            Player p = (Player) s;

            if(!p.hasPermission("zencore.cmd.healSelf")){
                chatInfo.sendMessage("general.noPermission",p);
                return true;
            }

            if(p.getHealth() == 0){
                chatInfo.sendMessage("messages.heal.couldNotHeal",p);
                return true;
            }

            p.setHealth(20.0);
            p.setFoodLevel(20);
            p.setFireTicks(0);
            chatInfo.sendMessage("messages.heal.healed",p);
            return true;
        }

        if(!s.hasPermission("zencore.cmd.healOthers")){
            chatInfo.sendMessage("general.noPermission",s);
            return true;
        }

        Player t = Bukkit.getPlayer(args[0]);

        if(t == null || !t.isOnline()){
            chatInfo.sendMessage("general.playerNotFound",s);
            return true;
        }

        if(t.getHealth() == 0){
            chatInfo.sendMessage("messages.heal.couldNotHeal",s);
            return true;
        }

        t.setHealth(20.0);
        t.setFoodLevel(20);
        t.setFireTicks(0);

        chatInfo.sendMessage("messages.heal.healedOther",s,"player",t.getName());
        chatInfo.sendMessage("messages.heal.healedByOther",t);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s,Command cmd,String label,String[] args){
        if(args.length == 1 && s.hasPermission("zencore.cmd.healOthers")){
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