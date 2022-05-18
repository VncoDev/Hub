package dev.vnco.hub.command.api.internal;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import dev.vnco.hub.command.api.annotation.Command;
import dev.vnco.hub.command.api.exception.ContextParseException;
import dev.vnco.hub.command.api.parameter.ParameterParser;
import com.google.common.collect.Maps;

import dev.vnco.hub.command.api.context.Base;
import dev.vnco.hub.command.api.context.Context;
import dev.vnco.hub.command.api.context.ContextParser;
import dev.vnco.hub.command.api.context.RootContext;

public class InternalContextParser implements ContextParser {

    private Map<String, Base> bases;
    private ParameterParser parameterParser;

    public InternalContextParser(ParameterParser parameterParser) {
        this.parameterParser = parameterParser;
        this.bases = Maps.newConcurrentMap();
    }

    @Override
    public <T> void append(Class<? extends T> aClass, T instance) {
        for (Method method : aClass.getDeclaredMethods()) {
            method.setAccessible(true);

            if (method.isAnnotationPresent(Command.class)) {
                Command command = method.getDeclaredAnnotation(Command.class);
                String name = command.aliases()[0];
                if (name == null || name.isEmpty()) {
                    throw new ContextParseException("Command doesn't have a name.");
                }

                bases.put(name, new ContextBase(command, method, instance));
            }
        }
    }

    @Override
    public Iterable<RootContext> collect() {
        Map<String, RootContext> rootContexts = Maps.newHashMap();
        Map<String, Context> contexts = Maps.newHashMap();

        for (Map.Entry<String, Base> entry : this.bases.entrySet()) {
            Base base = entry.getValue();
            String[] names = entry.getKey().split(" ");
            String rootName = names[0];

            if (names.length == 1) {
                // Found root context
                RootContext rootContext = getMergedIfPresent(rootContexts, rootName, new ExecutableRootContext(rootName, base.getMethod(), base.getInstance()));
                complete(rootContext, base);
                continue;
            }

            RootContext rootContext = rootContexts.get(rootName);
            if (rootContext == null) {
                // Generate root context
                rootContext = new ExecutableRootContext(rootName, null, null);
                rootContexts.put(rootName, rootContext);
            }

            Context parent = rootContext;
            String path = parent.getName();
            for (int i = 1; i < names.length; i++) {
                String name = names[i];
                path += name;

                if (i == names.length - 1) {
                    // Found context with parent
                    Context context = getMergedIfPresent(contexts, path, new ExecutableContext(parent, name, base.getMethod(), base.getInstance()));
                    parent.addChild(context);
                    complete(context, base);
                }

                Context context = contexts.get(path);
                if (context == null) {
                    // Generate context with parent
                    context = new ExecutableContext(parent, name, null, null);
                    parent.addChild(context);
                    contexts.put(path, context);
                }

                parent = context;
            }
        }

        bases.clear();
        return rootContexts.values();
    }

    private <C extends Context> C getMergedIfPresent(Map<String, C> map, String name, C context) {
        C aContext = map.get(name);

        if (aContext != null) {
            aContext.merge(context);
        } else {
            map.put(name, context);
            return (context);
        }

        return (aContext);
    }

    private <C extends Context> void complete(C context, Base base) {
        context.getAliases().addAll(Arrays.asList(base.getCommand().aliases()));
        context.getParameters().addAll(this.parameterParser.parse(base.getMethod()));
        context.setDescription(base.getCommand().description());
        context.setPermission(base.getCommand().permission());
        context.setAsync(base.getCommand().async());
        context.setHelp(base.getCommand().help());
    }
}
