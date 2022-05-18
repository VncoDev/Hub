package dev.vnco.hub.command.api.parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    
	String name();
	
	boolean wildcard() default (false);
	
	String defaultValue() default ("");
	
	boolean optional() default (false);
}