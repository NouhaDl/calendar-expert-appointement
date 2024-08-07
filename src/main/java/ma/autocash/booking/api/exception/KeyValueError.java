package ma.autocash.booking.api.exception;


public interface KeyValueError {
    Integer getId();
    String getMsgKey();
    Integer getHttpCode();
}