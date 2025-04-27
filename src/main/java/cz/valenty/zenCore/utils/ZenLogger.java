package cz.valenty.zenCore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ZenLogger{
    /**
     * public LoggerController info(@Nullable String message)
     *
     * @param message String {@link String}
     * @return LoggerController
     */
    public ZenLogger info(@Nullable String message){
        log(ZenLogger.Level.INFO,message);
        return this;
    }

    /**
     * public LoggerController warn(@Nullable String message)
     *
     * @param message String {@link String}
     * @return LoggerController
     */
    public ZenLogger warn(@Nullable String message){
        log(ZenLogger.Level.WARNING,message);
        return this;
    }

    /**
     * public LoggerController error(@Nullable String message)
     *
     * @param message String {@link String}
     * @return LoggerController
     */
    public ZenLogger error(@Nullable String message){
        log(ZenLogger.Level.ERROR,message);
        return this;
    }

    /**
     * public LoggerController severe(@Nullable String message)
     *
     * @param message String {@link String}
     * @return LoggerController
     */
    public ZenLogger severe(@Nullable String message){
        log(ZenLogger.Level.SEVERE,message);
        return this;
    }

    /**
     * public LoggerController debug(Object instance, @Nullable String message)
     *
     * @param instance Object {@link Object}
     * @param message  String {@link String}
     * @return LoggerController
     */
    public ZenLogger debug(Object instance,@Nullable String message){
        log(ZenLogger.Level.DEBUG,instance.getClass().getName().split("\\.")[instance.getClass().getName().split("\\.").length - 1]+" "+message);
        return this;
    }

    /**
     * private LoggerController log(@NotNull LoggerController.Level level, @Nullable String message)
     *
     * @param level   Level {@link ZenLogger.Level}
     * @param message String {@link String}
     * @return LoggerController
     */
    private ZenLogger log(@NotNull ZenLogger.Level level,@Nullable String message){
        Bukkit.getConsoleSender().sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',level.getPrefix()+(message != null? message:"null")));
        return this;
    }

    public enum Level{
        INFO(ChatColor.WHITE+"[INFO]"),
        WARNING(org.bukkit.ChatColor.GOLD+"[WARN]"),
        ERROR(org.bukkit.ChatColor.RED+"[ERROR]"),
        SEVERE(org.bukkit.ChatColor.DARK_RED+"[SEVERE]"),
        DEBUG(org.bukkit.ChatColor.YELLOW+"[DEBUG]");

        private @NotNull
        final String prefix;

        Level(@NotNull String prefix){
            this.prefix = prefix;
        }

        @org.jetbrains.annotations.Contract(pure = true)
        public final @NotNull String getPrefix(){
            return ChatColor.AQUA + "[ZenCore] | "+this.prefix+" ";
        }

        @Override
        public String toString(){
            return name();
        }
    }
}