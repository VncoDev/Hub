package dev.vnco.hub.message;

import dev.henko.message.api.Messenger;
import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.impl.DefaultMessenger;
import dev.henko.message.bukkit.source.YamlSource;
import dev.vnco.hub.Hub;
import dev.vnco.hub.api.message.BuildMessage;
import dev.vnco.hub.user.User;
import dev.vnco.hub.utils.ChatUtil;
import me.yushust.inject.InjectAll;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import javax.inject.Provider;

@InjectAll
public class MessengerProvider implements Provider<Messenger<String>> {

    private Hub hub;
    private BuildMessage buildMessage;

    @Override
    public Messenger<String> get() {
        MessengerConfig<String> config = new MessengerConfig<>();

        config.setSource(new YamlSource(hub, "language"));

        config.addEntity(CommandSender.class)
                        .setSender(CommandSender::sendMessage);

        config.addEntity(User.class)
                .setSender((entity, message) -> entity.getAsPlayer().sendMessage(message))
                .setPlaceholderProvider(

                        "user",
                        (entity, placeholder) -> buildMessage.of(entity.getAsPlayer(), placeholder)

                        );

        config.addInterceptor(ChatUtil::translate);

        return new DefaultMessenger(config);
    }
}
