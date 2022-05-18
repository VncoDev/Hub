package dev.vnco.hub.command.api.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import com.google.common.base.Preconditions;

import dev.vnco.hub.command.api.Key;
import dev.vnco.hub.command.api.binding.AnnotatedBindingBuilder;
import dev.vnco.hub.command.api.binding.Binding;
import dev.vnco.hub.command.api.binding.BindingBuilder;

public class InternalAnnotatedBindingBuilder<T> extends AbstractBindingBuilder<T> implements AnnotatedBindingBuilder<T> {

    public InternalAnnotatedBindingBuilder(Map<Key<?>, Binding<?>> map, InternalBinding<T> binding) {
        super(map, binding);
    }

    @Override
    public BindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType) {
        Preconditions.checkState(binding.getKey().getAnnotationType() == null, "Annotation type already bound.");
        
        binding.getKey().setAnnotationType(annotationType);
        
        return (this);
    }
}
