package dev.vnco.hub.command.api.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import dev.vnco.hub.command.api.exception.ParameterParseException;
import dev.vnco.hub.command.api.parameter.Parameter;
import dev.vnco.hub.command.api.parameter.ParameterAnnotation;
import dev.vnco.hub.command.api.parameter.ParameterParser;
import dev.vnco.hub.command.api.parameter.ParsedParameter;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import dev.vnco.hub.command.api.Key;
import dev.vnco.hub.command.api.binding.Binder;
import dev.vnco.hub.command.api.binding.Binding;
import dev.vnco.hub.command.api.defaults.enums.EnumBinding;

@AllArgsConstructor
public class InternalParameterParser implements ParameterParser {

    private Binder binder;

    @Override
    public List<Parameter<?>> parse(Method method) {
        List<Parameter<?>> parameters = Lists.newArrayList();

        for (java.lang.reflect.Parameter parameter : method.getParameters()) {
            Class<?> aClass = parameter.getType();
            boolean annotated = false;

            for (Annotation annotation : parameter.getAnnotations()) {
                if (annotation.annotationType().isAnnotationPresent(ParameterAnnotation.class)) {
                    annotated = true;
                }
            }

            Class<? extends Annotation> annotationType = annotated ? parameter.getDeclaredAnnotations()[0].annotationType() : null;
            Key<?> key = Key.get(aClass, annotationType);
            Binding<?> binding = binder.getBinding(key);

            if (binding == null) {
                if (aClass.isEnum()) {
                    binding = new EnumBinding(key);
                } else {
                    throw new ParameterParseException("Couldn't find a binding for %s%s", aClass, annotated ? " annotated with " + annotationType + "." : ".");
                }
            }

            parameters.add(new ParsedParameter<>(parameter, binding));
        }

        return (parameters);
    }

}
