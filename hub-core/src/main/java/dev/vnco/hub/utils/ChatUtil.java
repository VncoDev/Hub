package dev.vnco.hub.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

/**
 * vLib
 * By: @OldVnco
 */
@UtilityClass
public class ChatUtil {

    public String translate(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String translate(String... message) {
        for (String string : message) {
            return translate(string);
        }
        return null;
    }

    public List<String> translate(List<String> stringList){
        return stringList.stream().map(ChatUtil::translate).collect(Collectors.toList());
    }

    public void print(String... message){
        for (String log : message) {
            Bukkit.getConsoleSender().sendMessage(translate(log));
        }
    }

    public void toSender(CommandSender sender, List<String> stringList){
        for (String string : stringList) {
            sender.sendMessage(translate(string));
        }
    }

    public void toSender(CommandSender sender, String... longMessage){
        for (String message : longMessage) {
            sender.sendMessage(translate(message));
        }
    }

    public void toSender(CommandSender sender, String message){
        sender.sendMessage(translate(message));
    }

    public String strip(String message) {
        return ChatColor.stripColor(message);
    }

}
