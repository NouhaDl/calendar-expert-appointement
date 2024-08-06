package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.exception.BusinessException;
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
    public ResponseEntity<BookingDto> saveBooking(@Valid @RequestBody BookingDto bookingDto,
                                                  @RequestParam Long expertId) throws BusinessException {
        bookingService.saveBooking(bookingDto);
        BookingDto savedBooking = bookingService.getBookingById(bookingDto.getId());
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
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
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @Valid @RequestBody BookingDto bookingDto) throws BusinessException {
        bookingDto.setId(id);
        bookingService.updateBooking(bookingDto);
        BookingDto updatedBooking = bookingService.getBookingById(id);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Booking by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Booking not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) throws BusinessException {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) throws BusinessException {
        BookingDto booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping
    @Operation(summary = "Get all Bookings",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bookings found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "No bookings found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<List<BookingDto>> getAllBookings() throws BusinessException {
        List<BookingDto> bookings = bookingService.getAllBookings();
        return bookings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookings);
    }
}
