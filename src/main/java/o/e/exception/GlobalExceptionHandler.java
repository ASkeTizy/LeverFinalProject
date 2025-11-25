package o.e.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ControllerExceptionHandler> catchResourceNotFoundException(ResourceNotFoundException e) {
//        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ControllerExceptionHandler(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

}
