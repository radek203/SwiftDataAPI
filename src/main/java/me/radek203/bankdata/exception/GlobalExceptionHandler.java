package me.radek203.bankdata.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GlobalExceptionHandler handles exceptions globally and provides custom responses for different types of exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles ResourceNotFoundException and returns a custom error response.
     *
     * @param exception  the ResourceNotFoundException thrown.
     * @param webRequest the current web request.
     * @return ResponseEntity containing the error details.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleResourceNotFoundException(final ResourceNotFoundException exception, final WebRequest webRequest) {
        final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(), exception.getData(), webRequest.getDescription(false), ResourceNotFoundException.ERROR_CODE);

        return new ResponseEntity<>(errorDetails, ResourceNotFoundException.HTTP_STATUS);
    }

    /**
     * Handles ResourceAlreadyExistsException and returns a custom error response.
     *
     * @param exception  the ResourceAlreadyExistsException thrown.
     * @param webRequest the current web request.
     * @return ResponseEntity containing the error details.
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public final ResponseEntity<ErrorDetails> handleResourceAlreadyExistsException(final ResourceAlreadyExistsException exception, final WebRequest webRequest) {
        final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(), exception.getData(), webRequest.getDescription(false), ResourceAlreadyExistsException.ERROR_CODE);

        return new ResponseEntity<>(errorDetails, ResourceAlreadyExistsException.HTTP_STATUS);
    }

    /**
     * Handles MethodArgumentNotValidException and returns a custom error response with validation errors.
     *
     * @param ex      the MethodArgumentNotValidException thrown.
     * @param headers the HTTP headers.
     * @param status  the HTTP status code.
     * @param request the current web request.
     * @return ResponseEntity containing the validation errors.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        final Map<String, String> errors = new HashMap<>();
        final List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        errorList.forEach((error) -> {
            final String fieldName = ((FieldError) error).getField();
            final String message = error.getDefaultMessage();

            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
