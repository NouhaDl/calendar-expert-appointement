package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                    @ApiResponse(responseCode = "200", description = "Booking created successfully",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<BookingDto> saveBooking(@RequestBody BookingDto bookingDto) throws TechnicalException {
        BookingDto savedBooking = bookingService.saveBooking(bookingDto);
        return ResponseEntity.ok(savedBooking);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Booking by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Booking updated successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Booking not found")
            })
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto) throws TechnicalException {
        BookingDto updatedBooking = bookingService.updateBooking(id, bookingDto);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Booking by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Booking not found")
            })
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) throws TechnicalException {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Booking by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Booking found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Booking not found")
            })
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) throws TechnicalException {
        BookingDto booking = bookingService.getBookingById(id);
        return booking != null ?
                ResponseEntity.ok(booking) :
                ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Get all Bookings",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bookings found",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<List<BookingDto>> getAllBookings() throws TechnicalException {
        List<BookingDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
}
