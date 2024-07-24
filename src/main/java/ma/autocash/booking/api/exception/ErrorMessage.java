package ma.autocash.booking.api.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

@Setter
@Getter
public class ErrorMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String messageKey;
    private Integer errorId;
    private Integer httpCode;
    private String[] parameters;

    public ErrorMessage() {
        super();
    }

    public ErrorMessage(String messageKey, Integer errorId, Integer httpCode) {
        this.messageKey = messageKey;
        this.errorId = errorId;
        this.httpCode = httpCode;
    }

    public ErrorMessage(String messageKey, Integer errorId, Integer httpCode, String... parameters) {
        this.messageKey = messageKey;
        this.errorId = errorId;
        this.httpCode = httpCode;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "[" + messageKey + "," + errorId + "," + httpCode + Arrays.toString(parameters) + "]";
    }
}
