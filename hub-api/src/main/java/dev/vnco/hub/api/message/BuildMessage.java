package dev.vnco.hub.api.message;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface BuildMessage {

    default List<String> of(Player player, List<String> list) {
        return list.stream().map(s -> of(player, s)).collect(Collectors.toList());
    }

    String of(Player player, String text);

    default void matchAndExecute(String name, String text, Consumer<String> consumer) {
        Matcher matcher = Pattern.compile("(<" + Pattern.quote(name) + ":)(.+?)(>)").matcher(text);

        if (matcher.find()) {
            consumer.accept(matcher.group(2).trim());
        }
    }

    default String replace(Set<Map.Entry<String, String>> entrySet, String text) {
        for (Map.Entry<String, String> entry : entrySet) {
            String variable = entry.getKey(), value = entry.getValue();

            if (text.contains(variable)) {
                text = text.replace(variable, value);
            }
        }

        return text;
    }

}
