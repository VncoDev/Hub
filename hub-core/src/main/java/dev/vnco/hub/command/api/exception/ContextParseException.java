package dev.vnco.hub.command.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ContextParseException extends RuntimeException {

	private static final long serialVersionUID = -4572739622431255513L;

	public ContextParseException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ContextParseException(Throwable cause) {
        super(cause);
    }
}
