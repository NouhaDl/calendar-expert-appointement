package ma.autocash.booking.api.exception;

public class KeyValueErrorImpl implements KeyValueError {

    private String msgKey;
    private Integer id;
    private Integer httpCode;

    public  KeyValueErrorImpl (String msgKey, Integer id, Integer httpCode) {
        this.msgKey = msgKey;
        this.id = id;
        this.httpCode = httpCode;
    }

    @Override
    public String getMsgKey() {
        return msgKey;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getHttpCode() {
        return httpCode;
    }
}