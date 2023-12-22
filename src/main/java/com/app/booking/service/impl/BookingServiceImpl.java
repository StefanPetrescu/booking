package com.app.booking.service.impl;

import com.app.booking.exception.BookingException;
import com.app.booking.model.Booking;
import com.app.booking.repository.BookingRepository;
import com.app.booking.service.BookingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.app.booking.util.log.LoggingUtil.E50001;
import static com.app.booking.util.log.LoggingUtil.E50003;
import static com.app.booking.util.log.LoggingUtil.I10007;

@Service
public class BookingServiceImpl implements BookingService {
    private static final Logger LOGGER = LogManager.getLogger(BookingServiceImpl.class);
    private final BookingRepository bookingRepository;
    private final BlockServiceImpl blockService;

    public BookingServiceImpl(BookingRepository bookingRepository, BlockServiceImpl blockService) {
        this.bookingRepository = bookingRepository;
        this.blockService = blockService;
    }

    @Override
    public void create(Booking booking) {
        checkBookingAlreadyExists(booking);
        checkBlockAlreadyExists(booking);
        this.bookingRepository.save(booking);
    }

    @Override
    public void delete(Long id) {
        Optional<Booking> bookingById = this.bookingRepository.getBookingById(id);
        if (bookingById.isPresent()) {
            this.bookingRepository.deleteById(id);
        } else {
            LOGGER.error(E50003.getMessage(), id);
            throw new BookingException("Booking was not found in database");
        }
    }

    @Override
    public Booking update(Booking booking, Long id) {
        Optional<Booking> optionalBookingById = this.bookingRepository.findById(id);
        if (optionalBookingById.isPresent()) {
            Booking bookingById = optionalBookingById.get();
            LOGGER.info(I10007.getMessage(), id);
            checkBookingAlreadyExists(booking);
            checkBlockAlreadyExists(booking);
            bookingById.setId(id);
            bookingById.setPropertyName(booking.getPropertyName());
            bookingById.setStartDate(booking.getStartDate());
            bookingById.setEndDate(booking.getEndDate());
            bookingById.setFirstName(booking.getFirstName());
            bookingById.setLastName(booking.getLastName());
            bookingById.setNrOfGuests(booking.getNrOfGuests());
            return this.bookingRepository.save(bookingById);
        } else {
            LOGGER.error(E50003.getMessage(), id);
            throw new BookingException("Booking was not found in database");
        }
    }

    @Override
    public void checkBookingAlreadyExists(Booking booking) {
        Optional<Booking> optionalExistingBooking = bookingRepository.getBookingByPropertyNameAndDateBetweenExclusive(booking.getPropertyName(),
                booking.getStartDate(), booking.getEndDate());
        if (optionalExistingBooking.isPresent()) {
            Booking existingBooking = optionalExistingBooking.get();
            LOGGER.error(E50001.getMessage(), existingBooking.getStartDate(), existingBooking.getEndDate());
            throw new BookingException("There is an overlapping with a booking that starts on " + existingBooking.getStartDate() + " and ends on " + existingBooking.getEndDate());
        }
    }

    private void checkBlockAlreadyExists(Booking booking) {
        if (blockService.checkBlockOverlap(booking.getPropertyName(), booking.getStartDate(), booking.getEndDate())) {
            throw new BookingException("You can't make a reservation in this period because the owner decided to block the property.");
        }
    }
}
