package me.radek203.bankdata.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a resource already exists.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

    static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    @Getter
    private String data;

    public ResourceAlreadyExistsException(final String message, final String data) {
        super(message);
        this.data = data;
    }

}
