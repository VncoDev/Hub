package dev.vnco.hub.command.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProviderException extends RuntimeException {

	private static final long serialVersionUID = 3137993976475246164L;

	public ProviderException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ProviderException(Throwable cause) {
        super(cause);
    }
}
