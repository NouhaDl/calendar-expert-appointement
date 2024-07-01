package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.services.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto saveBooking(@RequestBody BookingDto bookingDto) {
        return bookingService.saveBooking(bookingDto);
    }

    @PutMapping("/{id}")
    public BookingDto updateBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto) {
        return bookingService.updateBooking(id, bookingDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }

    @GetMapping("/{id}")
    public BookingDto getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping
    public List<BookingDto> getAllBookings() {
        return bookingService.getAllBookings();
    }
}
