package Exceptions;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String registration_limit_breached) {
        super(registration_limit_breached);
    }
}
