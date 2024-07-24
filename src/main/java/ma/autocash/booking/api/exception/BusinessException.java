package ma.autocash.booking.api.exception;
import lombok.Getter;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BusinessException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorMessage errorMessage;
    private final List<ErrorMessage> subErrors;

    public BusinessException(KeyValueError keyValueError, String... args) {
        super(keyValueError.getMsgKey());
        errorMessage = new ErrorMessage(keyValueError.getMsgKey(), keyValueError.getId(), 400);
        subErrors = new ArrayList<>();
    }

    public BusinessException(Exception exception, KeyValueError keyValueError) {
        super(exception);
        errorMessage = new ErrorMessage(keyValueError.getMsgKey(), keyValueError.getId(), 400);
        subErrors = new ArrayList<>();
    }

    public BusinessException(KeyValueError keyValueError, List<ErrorMessage> subErrors, String... args) {
        super(keyValueError.getMsgKey());
        errorMessage = new ErrorMessage(keyValueError.getMsgKey(), keyValueError.getId(), 400);
        this.subErrors = subErrors;
    }

}
