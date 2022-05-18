package dev.vnco.hub.command.api.parameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import dev.vnco.hub.command.api.annotation.Sender;
import dev.vnco.hub.command.api.binding.Binding;

public interface Parameter<T> extends AnnotatedElement, Iterable<Annotation> {

    String getName();

    Binding<T> getBinding();

    default boolean isSender() {
        return (isAnnotationPresent(Sender.class));
    }

    default boolean isOptional() {
        return (getAnnotation(Param.class).optional());
    }

    default boolean isText() {
        return (getAnnotation(Param.class).wildcard());
    }

    default boolean isDefault() {
        return (!getAnnotation(Param.class).defaultValue().isEmpty());
    }

    default String getDefaultValue() {
        return (getAnnotation(Param.class).defaultValue());
    }

    default boolean isBooleanType() {
        final Class<T> type = getBinding().getKey().getType();
        return type.isInstance(Boolean.class) || type.isInstance(boolean.class);
    }
}
