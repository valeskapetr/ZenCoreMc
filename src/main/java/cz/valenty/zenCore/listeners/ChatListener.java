package cz.valenty.zenCore.listeners;

import cz.valenty.zenCore.ZenCore;
import cz.valenty.zenCore.utils.ChatInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.stream.Collectors;

public class ChatListener implements Listener{
    private final ZenCore plugin;
    private final ChatInfo chatInfo;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacySection();
    private LuckPerms luckPerms;

    public ChatListener(ZenCore plugin,ChatInfo chatInfo){
        this.plugin = plugin;
        this.chatInfo = chatInfo;
        this.luckPerms = (LuckPerms) plugin.getServer().getServicesManager().load(LuckPerms.class);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent evt){
        Player p = evt.getPlayer();
        String message = evt.getMessage();
        CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(p);
        String group = metaData.getPrimaryGroup();

        String formatPath = plugin.getConfig().getString("general.chatFormat." + group) != null? "general.chatFormat." + group:"general.chatFormat.default";
        String format = plugin.getConfig().getString(formatPath,"<prefix><gray><name></gray> <dark_gray>»</dark_gray> <white><message></white>");

        format = format.replace("<prefix>",metaData.getPrefix() != null? convertLegacyToMiniMessage(metaData.getPrefix()):"").replace("<suffix>",metaData.getSuffix() != null? convertLegacyToMiniMessage(metaData.getSuffix()):"").replace("<prefixes>",metaData.getPrefixes().keySet().stream().map(key -> convertLegacyToMiniMessage(metaData.getPrefixes().get(key))).collect(Collectors.joining())).replace("<suffixes>",metaData.getSuffixes().keySet().stream().map(key -> convertLegacyToMiniMessage(metaData.getSuffixes().get(key))).collect(Collectors.joining())).replace("<world>",p.getWorld().getName()).replace("<name>",p.getName()).replace("<displayname>",p.getDisplayName()).replace("<name-color>",metaData.getMetaValue("name-color") != null? metaData.getMetaValue("name-color"):"").replace("<message-color>",metaData.getMetaValue("message-color") != null? metaData.getMetaValue("message-color"):"").replace("<message>",convertHexColorsToMiniMessage(message));
        Component formattedMessage = miniMessage.deserialize(format);

        evt.setCancelled(true);
        plugin.getServer().broadcast(formattedMessage);
        ZenCore.getInstance().getDiscordBot().sendMessage(p.getName() + " » " + message);
    }

    private String convertLegacyToMiniMessage(String legacyText){
        Component component = legacySerializer.deserialize(legacyText);
        return miniMessage.serialize(component);
    }

    private String convertHexColorsToMiniMessage(String text){
        return text.replaceAll("#([A-Fa-f0-9]{6})","<#$1>");
    }
}