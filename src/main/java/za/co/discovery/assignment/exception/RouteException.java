package za.co.discovery.assignment.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception class
 */
public class RouteException extends RuntimeException {

    private HttpStatus httpStatus;

    public RouteException(String message) {
        super(message);
    }

    public RouteException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
