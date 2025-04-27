package cz.valenty.zenCore;

import cz.valenty.zenCore.commands.*;
import cz.valenty.zenCore.commands.admin.BroadcastCmd;
import cz.valenty.zenCore.commands.admin.ReloadCmd;
import cz.valenty.zenCore.listeners.ChatListener;
import cz.valenty.zenCore.listeners.PlayerListener;
import cz.valenty.zenCore.listeners.blockers.DisableBlockBreakListener;
import cz.valenty.zenCore.listeners.blockers.DisableBlockPlaceListener;
import cz.valenty.zenCore.listeners.blockers.ProtectedWorlds;
import cz.valenty.zenCore.listeners.discord.DiscordBot;
import cz.valenty.zenCore.utils.ChatInfo;
import cz.valenty.zenCore.utils.ZenLogger;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class ZenCore extends JavaPlugin{
    private static ZenLogger zLogger;
    private static ZenCore instance;
    private FileConfiguration translationsConfig;
    private ChatInfo chatInfo;
    private DiscordBot discordBot;
    private @Getter List<String> disabledBlockBreakWorlds = new ArrayList<>();
    private @Getter List<String> disabledBlockPlaceWorlds = new ArrayList<>();
    private @Getter List<String> enabledProtectWorlds = new ArrayList<>();
    private @Getter boolean disabledBlockBreak = false;
    private @Getter boolean disabledBlockPlace = false;
    private @Getter boolean protectedWorlds = false;

    @Override
    public void onEnable(){
        instance = this;
        zLogger = new ZenLogger();
        discordBot = new DiscordBot();

        //Banner
        startupBanner();
        zLogger.info("ZenCore is now starting up...");

        //Config
        zLogger.info("Setting up configuration & translation files...");

        saveResource("translations.yml",false);
        saveDefaultConfig();
        loadConfiguration();

        File translationsFile = new File(getDataFolder(),"translations.yml");
        translationsConfig = YamlConfiguration.loadConfiguration(translationsFile);
        chatInfo = new ChatInfo(translationsConfig);

        zLogger.info("Configuration & translation files successfully set.");

        //Cmds
        zLogger.info("Loading commands...");
        registerCommands();
        zLogger.info("Commands loaded.");

        //Managers

        //Listeners
        zLogger.info("Loading listeners & events...");
        registerListeners();
        zLogger.info("Listeners & events loaded.");

        discordBot.sendMessage(getConfig().getString("discord.messages.serverOnline"));
    }

    @Override
    public void onDisable(){
        zLogger.info("Plugin ZenCore is now shutting down...");
        discordBot.sendMessage(getConfig().getString("discord.messages.serverOffline"));
        discordBot.shutdown();
        zLogger.info("Discord bot shut down.");
        zLogger.info("Plugin shut down.");
    }

    private void loadConfiguration(){
        disabledBlockBreak = getConfig().getBoolean("general.blockBreak.enabled",false);
        if(disabledBlockBreak) getConfig().getList("general.blockBreak.worlds").forEach(world -> disabledBlockBreakWorlds.add((String) world));

        disabledBlockPlace = getConfig().getBoolean("general.blockPlace.enabled",false);
        if(disabledBlockPlace) getConfig().getList("general.blockPlace.worlds").forEach(world -> disabledBlockPlaceWorlds.add((String) world));

        protectedWorlds = getConfig().getBoolean("general.protectWorlds.enabled",false);
        if(protectedWorlds) getConfig().getList("general.protectWorlds.worlds").forEach(world -> enabledProtectWorlds.add((String) world));
    }

    private void startupBanner(){
        zLogger.info("");
        zLogger.info("─────────────────────────────────────────────────────────");
        zLogger.info("");
        zLogger.info("███████╗███████╗███╗  ██╗ █████╗  █████╗ ██████╗ ███████╗");
        zLogger.info("╚════██║██╔════╝████╗ ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝");
        zLogger.info("  ███╔═╝█████╗  ██╔██╗██║██║  ╚═╝██║  ██║██████╔╝█████╗  ");
        zLogger.info("██╔══╝  ██╔══╝  ██║╚████║██║  ██╗██║  ██║██╔══██╗██╔══╝  ");
        zLogger.info("███████╗███████╗██║ ╚███║╚█████╔╝╚█████╔╝██║  ██║███████╗");
        zLogger.info("╚══════╝╚══════╝╚═╝  ╚══╝ ╚════╝  ╚════╝ ╚═╝  ╚═╝╚══════╝");
        zLogger.info("                          ZenCore v1.0, made by ValentyCZ");
        zLogger.info("");
        zLogger.info("─────────────────────────────────────────────────────────");
        zLogger.info("");
    }

    public void reload(){
        zLogger.info("Reloading plugin...");
        reloadConfig();
        zLogger.info("Plugin reloaded.");
    }

    private void registerCommands(){
        //Admin cmds
        getCommand("broadcast").setExecutor(new BroadcastCmd(this,chatInfo));
        getCommand("reload").setExecutor(new ReloadCmd(this,chatInfo));

        //Player cmds
        getCommand("heal").setExecutor(new HealCmd(this,chatInfo));
        getCommand("fly").setExecutor(new FlyCmd(this,chatInfo));
        getCommand("nick").setExecutor(new NickCmd(this,chatInfo));
        getCommand("glow").setExecutor(new GlowCmd(this,chatInfo));
    }

    private void registerListeners(){
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerListener(chatInfo),this);
        pm.registerEvents(new DisableBlockBreakListener(chatInfo),this);
        pm.registerEvents(new DisableBlockPlaceListener(chatInfo),this);
        pm.registerEvents(new ChatListener(this,chatInfo),this);
        pm.registerEvents(new ProtectedWorlds(),this);
    }

    public FileConfiguration getTranslations(){
        return translationsConfig;
    }

    public static ZenCore getInstance(){
        return instance;
    }

    public static ZenLogger getZenLogger(){
        return zLogger;
    }

    public List<String> getDisabledBlockBreakWorlds(){
        return disabledBlockBreakWorlds;
    }

    public List<String> getDisabledBlockPlaceWorlds(){
        return disabledBlockPlaceWorlds;
    }
    public List<String> getProtectedWorlds(){
        return enabledProtectWorlds;
    }

    public DiscordBot getDiscordBot(){
        return discordBot;
    }
}