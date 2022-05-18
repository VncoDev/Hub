package dev.vnco.hub.command.api.internal;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import dev.vnco.hub.command.api.annotation.Sender;
import dev.vnco.hub.command.api.exception.ArgumentFormatException;
import dev.vnco.hub.command.api.exception.ProviderException;
import dev.vnco.hub.command.api.parameter.Parameter;
import com.google.common.collect.Lists;

import dev.vnco.hub.command.api.ArgumentFormatter;
import dev.vnco.hub.command.api.CommandSource;
import dev.vnco.hub.command.api.Provider;
import dev.vnco.hub.command.api.context.Context;

public class InternalArgumentFormatter implements ArgumentFormatter {

    @Override
    public Object[] format(CommandSource<?> source, Context context, String... args) {
        Object[] arguments = new Object[context.getParameters().size()];

        int i = 0;
        int j = 0;
        boolean seenOptional = false;
        for (Parameter<?> parameter : context.getParameters()) {
            Provider provider = parameter.getBinding().getProvider();

            if (parameter.isAnnotationPresent(Sender.class)) {
                arguments[i] = provider.provide(source, "", parameter);
                i++;
                continue;
            }

            if (parameter.isOptional() || parameter.isDefault()) {
                seenOptional = true;
            } else {
                if (seenOptional) {
                    throw new ArgumentFormatException("Required parameters are only allowed before optional/default ones.");
                }
            }

            if (j > args.length - 1) {
                if (parameter.isOptional() && !parameter.isDefault()) {
                    arguments[i] = null;
                    i++;
                    j++;
                    continue;
                } else if (parameter.isDefault()) {
                    arguments[i] = provider.provide(source, parameter.getDefaultValue(), parameter);
                    i++;
                    j++;
                    continue;
                } else {
                    throw new ArgumentFormatException();
                }
            }

            String arg = args[j];

            if (parameter.isOptional() && !parameter.isDefault()) {
                try {
                    arguments[i] = provider.provide(source, arg, parameter);
                } catch (ProviderException ignored) {
                    arguments[i] = null;
                }
            } else if (parameter.isDefault()) {
                try {
                    arguments[i] = provider.provide(source, arg, parameter);
                } catch (ProviderException ignored) {
                    arguments[i] = provider.provide(source, parameter.getDefaultValue(), parameter);
                }
            } else if (parameter.isText()) {
                StringBuilder builder = new StringBuilder();
                for (int k = j; k < args.length; k++) {
                    builder.append(args[k]);
                    if (k != args.length - 1) {
                        builder.append(" ");
                    }
                }

                arguments[i] = builder.toString();
            } else {
                arguments[i] = provider.provide(source, arg, parameter);
            }

            j++;
            i++;
        }

        return (arguments);
    }

    @Override
    public List<String> suggest(CommandSource<?> source, Context context, String lastWord, int pos) {
        List<Parameter<?>> suggestingParameters = Lists.newArrayList();

        for (Parameter<?> parameter : context.getParameters()) {
            if (parameter.isAnnotationPresent(Sender.class)) {
                continue;
            }

            suggestingParameters.add(parameter);
        }

        try {
            Parameter<?> parameter = suggestingParameters.get(pos);

            if (parameter == null) {
                return (Collections.emptyList());
            }

            Provider provider = parameter.getBinding().getProvider();
            List<String> result = provider.suggest(source, parameter);
            return (result.stream()
                    .filter(s -> s.toLowerCase().startsWith(lastWord.toLowerCase()))
                    .sorted()
                    .collect(Collectors.toList()
                    )
            );
        } catch (IndexOutOfBoundsException e) {
            return (Collections.emptyList());
        }
    }
}
