package ma.autocash.booking.api.exception;

import java.io.Serializable;
import java.util.Arrays;

public class ErrorMessage implements Serializable {

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

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public Integer getErrorId() {
        return errorId;
    }

    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "[" + messageKey + "," + errorId + "," + httpCode + Arrays.toString(parameters) + "]";
    }
}
