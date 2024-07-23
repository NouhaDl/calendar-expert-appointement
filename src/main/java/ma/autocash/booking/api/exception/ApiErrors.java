package ma.autocash.booking.api.exception;
public enum ApiErrors implements KeyValueError {

    // NOT FOUND
    USER_NOT_FOUND(4041, 404, "error.users.notFound"),

    // CONFLICT
    USER_ALREADY_EXISTS(4091, 409, "error.users.exists"),

    // TECHNICAL ERROR
    TECHNICAL_ERROR(5001, 500, "error.technical"),
    NOTIFICATION_ERROR(5002, 500, "error.technical.notification");

    private final Integer id;
    private final Integer httpCode;
    private final String msgKey;

    ApiErrors(Integer id, Integer httpCode, String msgKey) {
        this.id = id;
        this.httpCode = httpCode;
        this.msgKey = msgKey;
    }
    @Override
    public Integer getHttpCode() {
        return this.httpCode;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getMsgKey() {
        return this.msgKey;
    }
}
