package cz.valenty.zenCore.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ChatInfo{
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final FileConfiguration translations;
    private final String prefix;

    public ChatInfo(FileConfiguration translations){
        this.translations = translations;
        this.prefix = translations.getString("general.prefix","");
    }

    public void sendMessage(@NotNull String path,@NotNull Audience audience){
        sendMessage(path,audience,(Map<String,String>) null);
    }

    public void sendMessage(@NotNull String path,@NotNull Audience audience,@Nullable Map<String,String> placeholders){
        String rawMessage = translations.getString(path);
        if(rawMessage == null) rawMessage = "<red>Message '"+path+"' wasn't found in config!";

        TagResolver.Builder resolver = buildResolver(placeholders);
        audience.sendMessage(miniMessage.deserialize(prefix+rawMessage,resolver.build()));
    }

    public void sendRaw(@NotNull String message, @NotNull Audience audience, @Nullable Map<String, String> placeholders){
        TagResolver.Builder resolver = buildResolver(placeholders);
        audience.sendMessage(miniMessage.deserialize(message, resolver.build()));
    }

    public void sendRaw(@NotNull String message, @NotNull Audience audience, String... placeholders){
        if(placeholders.length % 2 != 0) throw new IllegalArgumentException("The number of placeholders must be even (key, value)");

        Map<String, String> placeholderMap = new HashMap<>();
        for(int i = 0; i < placeholders.length; i += 2) placeholderMap.put(placeholders[i],placeholders[i+1]);

        sendRaw(message, audience, placeholderMap);
    }

    public void sendMessage(@NotNull String path,@NotNull Audience audience,String... placeholders){
        if(placeholders.length % 2 != 0) throw new IllegalArgumentException("The number of placeholders must be even (in key, value format)");

        Map<String, String> placeholderMap = new HashMap<>();
        for(int i = 0; i < placeholders.length; i += 2) placeholderMap.put(placeholders[i],placeholders[i+1]);

        sendMessage(path,audience,placeholderMap);
    }

    public RawMessageBuilder prepareRaw(@NotNull String path){
        return new RawMessageBuilder(path);
    }

    public MessageBuilder prepareMessage(@NotNull String path){
        return new MessageBuilder(path);
    }

    private TagResolver.Builder buildResolver(@Nullable Map<String,String> placeholders){
        TagResolver.Builder resolver = TagResolver.builder();
        resolver.resolver(Placeholder.parsed("prefix",prefix));

        if(placeholders != null) placeholders.forEach((key,value) -> resolver.resolver(Placeholder.parsed(key, value)));

        return resolver;
    }

    public class MessageBuilder{
        private final String path;
        private final Map<String,String> placeholders = new HashMap<>();

        public MessageBuilder(String path){
            this.path = path;
        }

        public MessageBuilder replace(String key,String value){
            placeholders.put(key,value);
            return this;
        }

        public void sendTo(Audience audience){
            ChatInfo.this.sendMessage(path,audience,placeholders);
        }
    }

    public class RawMessageBuilder{
        private final String path;
        private final Map<String,String> placeholders = new HashMap<>();

        public RawMessageBuilder(String path){
            this.path = path;
        }

        public RawMessageBuilder replace(String key,String value){
            placeholders.put(key, value);
            return this;
        }

        public void sendTo(Audience audience){
            String rawMessage = translations.getString(path);
            if(rawMessage == null) rawMessage = "<red>Translation for '"+path+"' not found!";
            sendRaw(rawMessage, audience, placeholders);
        }
    }
}