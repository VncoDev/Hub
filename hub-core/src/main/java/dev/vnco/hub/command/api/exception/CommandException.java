package dev.vnco.hub.command.api.exception;

import lombok.AllArgsConstructor;
import dev.vnco.hub.command.api.context.Context;

@AllArgsConstructor
public class CommandException extends RuntimeException {

	private static final long serialVersionUID = -5123399422381119603L;
	private Context context;

    public CommandException(Context context, String message, Object... args) {
        super(String.format(message, args));
        
        this.context = context;
    }

    public Context getContext() {
        return (context);
    }
}
