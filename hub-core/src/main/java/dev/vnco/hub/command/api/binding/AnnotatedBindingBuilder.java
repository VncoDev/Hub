package dev.vnco.hub.command.api.binding;

import java.lang.annotation.Annotation;

public interface AnnotatedBindingBuilder<T> extends BindingBuilder<T> {

    BindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType);

}
