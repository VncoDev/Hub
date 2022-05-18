package dev.vnco.hub.api.model;

public interface Service {

    void start();

    default void stop() {}

    default int getPriority() {
        return 0;
    }

}
