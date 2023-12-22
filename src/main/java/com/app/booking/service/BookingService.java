package com.app.booking.service;

import com.app.booking.model.Booking;

public interface BookingService {
    void create(Booking booking);

    void delete(Long id);

    Booking update(Booking booking, Long id);

    void checkBookingAlreadyExists(Booking booking);
}
