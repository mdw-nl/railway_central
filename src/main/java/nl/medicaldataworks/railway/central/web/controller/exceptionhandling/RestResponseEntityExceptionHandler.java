package nl.medicaldataworks.railway.central.web.controller.exceptionhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<RestResponse<String>> handleUnknownException(IllegalArgumentException exception, WebRequest request) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new RestResponse<>(Boolean.FALSE, exception.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}