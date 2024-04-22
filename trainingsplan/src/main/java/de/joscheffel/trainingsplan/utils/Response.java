package de.joscheffel.trainingsplan.utils;

import java.util.Objects;

/**
 * This Response record serves as data transfer object with error forwarding. It works similar like
 * an {@link java.util.Optional}, just with the advantage of error message forwarding.
 *
 * @param entity       The entity which should get forwarded.
 * @param errorMessage The error message which might have occurred if no entity was found.
 * @param <T>          The type of the entity
 */
public record Response<T>(T entity, String errorMessage) {

    /**
     * Returns Response of type of the responseEntity
     *
     * @param entity The entity of the response
     * @param <T>    type of the entity
     * @return {@link Response<T>}
     */
    public static <T> Response<T> of(T entity) {
        return new Response<T>(entity, null);
    }

    /**
     * Returns Response with an error message and without an entity.
     *
     * @param errorMessage The error message of the occurred error.
     * @return {@link Response} with an error message
     */
    public static <T> Response<T> error(String errorMessage) {
        return new Response<>(null, errorMessage);
    }

    /**
     * Returns a boolean whether an error occurred or no error occurred
     *
     * @return boolean, true if error occurred otherwise false
     */
    public boolean isError() {
        return Objects.nonNull(errorMessage);
    }
}
