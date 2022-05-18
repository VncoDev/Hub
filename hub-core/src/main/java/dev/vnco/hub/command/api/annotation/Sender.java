package dev.vnco.hub.command.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.vnco.hub.command.api.parameter.ParameterAnnotation;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@ParameterAnnotation
public @interface Sender {
	
}
