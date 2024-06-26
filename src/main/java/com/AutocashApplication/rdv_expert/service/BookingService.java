package com.AutocashApplication.rdv_expert.service;

import com.AutocashApplication.rdv_expert.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking saveBooking(Booking booking);
    Booking updateBooking(Booking booking);
    void deleteBooking(Long id);
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
}
