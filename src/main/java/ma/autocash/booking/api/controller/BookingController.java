package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.provider.BookingProvider;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingProvider bookingProvider;

    public BookingController(BookingProvider bookingProvider) {
        this.bookingProvider = bookingProvider;
    }

    @PostMapping
    public Booking saveBooking(@RequestBody Booking booking) {
        return bookingProvider.saveBooking(booking);
    }

    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        // Assuming the booking object already has the ID set
        booking.setId(id);
        return bookingProvider.updateBooking(booking);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingProvider.deleteBooking(id);
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingProvider.getBookingById(id);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingProvider.getAllBookings();
    }
}
