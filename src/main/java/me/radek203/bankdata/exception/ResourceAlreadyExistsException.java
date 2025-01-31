package me.radek203.bankdata.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a resource already exists.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceAlreadyExistsException extends RuntimeException {

    static final String ERROR_CODE = "RESOURCE_ALREADY_EXISTS";
    static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    @Getter
    private String data;

    public ResourceAlreadyExistsException(final String message, final String data) {
        super(message);
        this.data = data;
    }

}
