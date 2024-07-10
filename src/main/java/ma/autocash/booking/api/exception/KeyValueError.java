package ma.autocash.booking.api.exception;

public interface KeyValueError {

Integer getHttpCode();
    Integer getId();
    String getMsgKey();
}