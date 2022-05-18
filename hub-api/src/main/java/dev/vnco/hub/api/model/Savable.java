package dev.vnco.hub.api.model;

import java.util.UUID;

/**
 * A useful interface to identify classes can be stored in the database
 *
 * Credits to: Sintaxis
 */

public interface Savable<O> {

    /**
     * The id of the object to save in the database
     *
     * @return The id of the object
     */

    O getId();

    default void setId(O id) {
        if (id instanceof UUID) {
            throw new RuntimeException("Cannot change immutable id");
        }
    }

}