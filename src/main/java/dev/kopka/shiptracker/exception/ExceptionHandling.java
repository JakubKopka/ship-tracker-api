package dev.kopka.shiptracker.exception;

import dev.kopka.shiptracker.domain.HttpResponse;
import dev.kopka.shiptracker.exception.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
        HttpResponse httpResponse = new HttpResponse(
                httpStatus.value(),
                httpStatus,
                httpStatus.getReasonPhrase(),
                message);
        return new ResponseEntity<>(httpResponse, httpStatus);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(DestinationNotFoundException.class)
    public ResponseEntity<HttpResponse> destinationNotFoundException(DestinationNotFoundException e){
        return createHttpResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(BlockedDestinationException.class)
    public ResponseEntity<HttpResponse> blockedDestinationException(BlockedDestinationException e){
        return createHttpResponse(NOT_ACCEPTABLE, e.getMessage());
    }

    @ExceptionHandler(DestinationFormatException.class)
    public ResponseEntity<HttpResponse> destinationFormatException(DestinationFormatException e){
        return createHttpResponse(NOT_ACCEPTABLE, e.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<HttpResponse> apiException(ApiException e){
        return createHttpResponse(INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<HttpResponse> tokenNotFoundException(TokenNotFoundException e){
        return createHttpResponse(INTERNAL_SERVER_ERROR, e.getMessage());
    }
}