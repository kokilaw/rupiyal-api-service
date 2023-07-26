package io.kokilaw.rupiyal.exception;

/**
 * Created by kokilaw on 2023-07-05
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
