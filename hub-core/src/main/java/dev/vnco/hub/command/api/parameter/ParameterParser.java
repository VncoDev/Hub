package dev.vnco.hub.command.api.parameter;

import java.lang.reflect.Method;
import java.util.List;

public interface ParameterParser {

    List<Parameter<?>> parse(Method method);

}
