package ma.autocash.booking.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException ex) {
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.valueOf(ex.getErrorMessage().getHttpCode()));
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<ErrorMessage> handleTechnicalException(TechnicalException ex) {
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.valueOf(ex.getErrorMessage().getHttpCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
