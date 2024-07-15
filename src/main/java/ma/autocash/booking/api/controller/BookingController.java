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

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingDto bookingDto) throws BusinessException, TechnicalException {
        try {
            BookingDto savedBooking = bookingService.saveBooking(bookingDto);
            return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
        } catch (TechnicalException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error creating booking", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @Valid @RequestBody BookingDto bookingDto) throws BusinessException, TechnicalException {
        try {
            BookingDto updatedBooking = bookingService.updateBooking(id, bookingDto);
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } catch (BusinessException e) {
            throw e;
        } catch (TechnicalException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error updating booking", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) throws BusinessException, TechnicalException {
        try {
            bookingService.deleteBooking(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BusinessException e) {
            throw e;
        } catch (TechnicalException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error deleting booking", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings() throws TechnicalException {
        try {
            List<BookingDto> bookings = bookingService.getAllBookings();
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (TechnicalException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving all bookings", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) throws BusinessException, TechnicalException {
        try {
            BookingDto bookingDto = bookingService.getBookingById(id);
            return new ResponseEntity<>(bookingDto, HttpStatus.OK);
        } catch (BusinessException e) {
            throw e;
        } catch (TechnicalException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving booking by id", e);
        }
    }
}
