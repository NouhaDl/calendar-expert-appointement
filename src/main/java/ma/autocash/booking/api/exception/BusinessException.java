package ma.autocash.booking.api.exception;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BusinessException extends Exception {
    private final KeyValueError keyValueError;
    private final List<KeyValueError> subErrors;

    public BusinessException(KeyValueError keyValueError) {
        super(keyValueError.getMsgKey());
        this.keyValueError = keyValueError;
        this.subErrors = new ArrayList<>();
    }

    public BusinessException(KeyValueError keyValueError, List<KeyValueError> subErrors) {
        super(keyValueError.getMsgKey());
        this.keyValueError = keyValueError;
        this.subErrors = subErrors;
    }
}
