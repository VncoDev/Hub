package dev.vnco.hub.command.api;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

public class Key<T> {

    private Class<T> type;
    private Class<? extends Annotation> annotationType;
    private int hashCode;

    private Key(Class<T> type, @Nullable Class<? extends Annotation> annotationType) {
        this.type = type;
        this.annotationType = annotationType;
        this.hashCode = this.type.hashCode() * 17 + (annotationType == null ? 0 : annotationType.hashCode());
    }

    private Key(Class<T> type) {
        this(type, null);
    }

    public static <T> Key<T> get(Class<T> aClass) {
        return new Key<>(aClass);
    }

    public static <T> Key<T> get(Class<T> aClass, Class<? extends Annotation> annotationType) {
        return new Key<>(aClass, annotationType);
    }

    public Class<T> getType() {
        return (type);
    }

    public Class<? extends Annotation> getAnnotationType() {
        return (annotationType);
    }

    public void setAnnotationType(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
        this.hashCode = this.type.hashCode() * 17 + (annotationType == null ? 0 : annotationType.hashCode());
    }

    @Override
    public int hashCode() {
        return (this.hashCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Key) {
            Key<?> key = (Key<?>) obj;

            if (!key.type.equals(this.type)) {
                return (false);
            }

            if (key.annotationType != null) {
                return (equalsWithAnnotation(key.annotationType));
            }

            return (true);
        }

        return (super.equals(obj));
    }

    private boolean equalsWithAnnotation(Class<? extends Annotation> annotationType) {
        if (this.annotationType == null) {
            return (false);
        }

        return (this.annotationType.equals(annotationType));
    }
}
