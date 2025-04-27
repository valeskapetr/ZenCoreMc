package cz.valenty.zenCore.listeners.discord;

import cz.valenty.zenCore.ZenCore;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class DiscordBot{
    private JDA jda;

    public DiscordBot(){
        String token = ZenCore.getInstance().getConfig().getString("discord.botToken","");

        if(token.isEmpty()){
            ZenCore.getZenLogger().warn("Discord Bot token is not set!");
            return;
        }

        try{
            jda = JDABuilder.createDefault(token).build().awaitReady();
        }catch(Exception e){
            ZenCore.getZenLogger().error(e.getMessage());
        }
    }

    public void sendMessage(String content){
        if(jda == null) return;

        String channelId = ZenCore.getInstance().getConfig().getString("discord.channelId","");
        TextChannel channel = jda.getTextChannelById(channelId);

        if(channel != null) channel.sendMessage(content).queue();
    }

    public void shutdown(){
        if(jda != null) jda.shutdown();
    }
}