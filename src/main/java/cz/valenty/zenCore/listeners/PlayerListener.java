package cz.valenty.zenCore.listeners;

import cz.valenty.zenCore.ZenCore;
import cz.valenty.zenCore.enums.MinecraftAdvancement;
import cz.valenty.zenCore.utils.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{
    private final ChatInfo chatInfo;

    public PlayerListener(ChatInfo chatInfo){
        this.chatInfo = chatInfo;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt){
        evt.joinMessage(null);
        chatInfo.prepareMessage("general.playerJoin").replace("player",evt.getPlayer().getName()).sendTo(Bukkit.getServer());

        String discordMsg = ZenCore.getInstance().getConfig().getString("discord.messages.join", "{player} joined the server.").replace("{player}",evt.getPlayer().getName());
        ZenCore.getInstance().getDiscordBot().sendMessage(discordMsg);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt){
        evt.quitMessage(null);
        chatInfo.prepareMessage("general.playerQuit").replace("player",evt.getPlayer().getName()).sendTo(Bukkit.getServer());

        String discordMsg = ZenCore.getInstance().getConfig().getString("discord.messages.leave", "{player} has left the server.").replace("{player}",evt.getPlayer().getName());
        ZenCore.getInstance().getDiscordBot().sendMessage(discordMsg);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent evt){
        evt.deathMessage(null);
        chatInfo.prepareMessage("general.playerDeath").replace("player",evt.getPlayer().getName()).sendTo(Bukkit.getServer());

        String discordMsg = ZenCore.getInstance().getConfig().getString("discord.messages.death", "{player} has died.").replace("{player}",evt.getPlayer().getName());
        ZenCore.getInstance().getDiscordBot().sendMessage(discordMsg);
    }

    @EventHandler
    public void onPlayerAdvancementGet(PlayerAdvancementDoneEvent evt) {
        Advancement adv = evt.getAdvancement();
        Player p = evt.getPlayer();
        String key = adv.getKey().getKey();
        String advName = MinecraftAdvancement.getDisplayNameByKey(key);
        if(advName == null) return;

        evt.message(null);
        chatInfo.prepareMessage("messages.advancements.playerGet").replace("player",p.getName()).replace("advancement",advName).sendTo(p);
        chatInfo.prepareMessage("messages.advancements.playerGetNotify").replace("player",p.getName()).replace("advancement",advName).sendTo(Bukkit.getServer());

        String discordMsg = ZenCore.getInstance().getConfig().getString("messages.advancement","**{player}** has get advancement {advancement}").replace("{player}", evt.getPlayer().getName()).replace("{advancement}",advName);
        ZenCore.getInstance().getDiscordBot().sendMessage(discordMsg);
    }
}