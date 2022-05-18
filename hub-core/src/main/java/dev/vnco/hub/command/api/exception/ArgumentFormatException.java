package dev.vnco.hub.command.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ArgumentFormatException extends RuntimeException {
	
	private static final long serialVersionUID = 2607125649812039167L;

	public ArgumentFormatException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ArgumentFormatException(Throwable cause) {
        super(cause);
    }
}
