package ma.autocash.booking.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiErrors implements KeyValueError {
    AVAILABILITY_NOT_FOUND(404, 404, "Availability.get.notfound"),

    BOOKING_NOT_FOUND(404, 404, "Booking.get.notfound"),

    EXPERT_NOT_FOUND(404, 404, "Expert.get.notfound"),

    ZONE_NOT_FOUND(404, 404, "Zone.get.notfound");

    private final Integer id;
    private final Integer httpCode;
    private final String msgKey;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public Integer getHttpCode() {
        return this.httpCode;
    }

    @Override
    public String getMsgKey() {
        return this.msgKey;
    }
}
