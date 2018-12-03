package cz.muni.fi.pa165.service.exception;

public class EntityValidationException extends Exception {

    public EntityValidationException() {
    }

    public EntityValidationException(String message) {
        super(message);
    }

    public EntityValidationException(Throwable cause) {
        super(cause);
    }

    public EntityValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
