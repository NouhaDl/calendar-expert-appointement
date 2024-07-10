package ma.autocash.booking.api.exception;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class TechnicalException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorMessage errorMessage;
    private final List<ErrorMessage> subErrors;

    public TechnicalException(KeyValueError keyValueError, String... args) {
        super(keyValueError.getMsgKey());
        errorMessage = new ErrorMessage(keyValueError.getMsgKey(), keyValueError.getId(), keyValueError.getHttpCode(), args);
        subErrors = new ArrayList<>();
    }

    public TechnicalException(Exception exception, KeyValueError keyValueError) {
        super(exception);
        errorMessage = new ErrorMessage(keyValueError.getMsgKey(), keyValueError.getId(), keyValueError.getHttpCode());
        subErrors = new ArrayList<>();
    }

    public TechnicalException(KeyValueError keyValueError, List<ErrorMessage> subErrors, String... args) {
        super(keyValueError.getMsgKey());
        errorMessage = new ErrorMessage(keyValueError.getMsgKey(), keyValueError.getId(), keyValueError.getHttpCode(), args);
        this.subErrors = subErrors;
    }


    public TechnicalException(String errorMessage, Exception e) {
        super(errorMessage, e);
        this.errorMessage = null;
        this.subErrors = new ArrayList<>();
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public List<ErrorMessage> getSubErrors() {
        return subErrors;
    }
}
