package dev.vnco.hub.command.api.defaults;

import java.time.Duration;
import java.time.format.DateTimeParseException;

import dev.vnco.hub.command.api.exception.ProviderException;
import dev.vnco.hub.command.api.parameter.Parameter;
import dev.vnco.hub.command.api.CommandSource;
import dev.vnco.hub.command.api.Provider;

public class DurationProvider implements Provider<Duration> {

    @Override
    public Duration provide(final CommandSource<?> source, final String string, final Parameter<Duration> parameter) {
        if (string.equalsIgnoreCase("oo") || string.equalsIgnoreCase("infinity") || string.equalsIgnoreCase("infinite") || string.equalsIgnoreCase("permanent") || string.equalsIgnoreCase("perm") || string.equalsIgnoreCase("forever")) {
            return Duration.ofMillis(Integer.MAX_VALUE);
        }

        if (string.matches("[0-9]*")) {
            return Duration.ofSeconds(Integer.parseInt(string));
        }

        try {
            String[] parts = string.split("d");

            if (parts.length == 1) {
                return Duration.parse((string.contains("d") ? "P" : "PT") + string);
            } else {
                return Duration.parse("P" + parts[0] + "dT" + parts[1]);
            }
        } catch (DateTimeParseException e) {
            throw new ProviderException("No parse duration '" + string + "' found.", e);
        }
    }
}
