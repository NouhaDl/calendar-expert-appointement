package ma.autocash.booking.api.controller;
import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping
    @Operation(summary = "Create a new Booking",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Booking created successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingDto bookingDto) {

        try {
            BookingDto savedBooking = bookingService.saveBooking(bookingDto);
            return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
        } catch (TechnicalException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Booking by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Booking updated successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Booking not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @Valid @RequestBody BookingDto bookingDto) {
        try {
            BookingDto updatedBooking = bookingService.updateBooking(id, bookingDto);
            return ResponseEntity.ok(updatedBooking);
        } catch (TechnicalException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
    @DeleteMapping("/{id}")

    @Operation(summary = "Delete a Booking by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Booking not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        try {
            bookingService.deleteBooking(id);
            return ResponseEntity.noContent().build();
        } catch (TechnicalException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get a Booking by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Booking found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Booking not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
        try {
            BookingDto bookingDto = bookingService.getBookingById(id);
            return ResponseEntity.ok(bookingDto);
        } catch (TechnicalException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
   
        }
    }
    @GetMapping
    @Operation(summary = "Get all Bookings",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bookings found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        try {
            List<BookingDto> bookings = bookingService.getAllBookings();
            return ResponseEntity.ok(bookings);
        } catch (TechnicalException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
}
