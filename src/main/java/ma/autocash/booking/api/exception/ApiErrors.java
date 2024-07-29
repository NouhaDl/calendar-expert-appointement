package ma.autocash.booking.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ApiErrors{

    // Define constants for all API errors
     AVAILABILITY_NOT_FOUND("Availability.get.notfound", 404, 404),

    BOOKING_NOT_FOUND("Booking.get.notfound", 404, 404),

    BOOKING_UPDATE_NOT_FOUND("Booking.update.notfound", 404, 404),

    BOOKING_DELETE_NOT_FOUND("Booking.delete.notfound", 404, 404),

    EXPERT_NOT_FOUND("Expert.get.notfound", 404, 404),

    ZONE_NOT_FOUND("Zone.get.notfound" , 404, 404),;

    private final String message;
    private final  int code;
    private  final int httpStatus;


}