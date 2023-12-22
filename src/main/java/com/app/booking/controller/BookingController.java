package com.app.booking.controller;

import com.app.booking.model.Booking;
import com.app.booking.service.BookingService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.app.booking.util.log.LoggingUtil.I10001;
import static com.app.booking.util.log.LoggingUtil.I10002;
import static com.app.booking.util.log.LoggingUtil.I10003;

@RestController()
@RequestMapping("/booking")
public class BookingController {
    private static final Logger LOGGER = LogManager.getLogger(BookingController.class);
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping()
    ResponseEntity<Booking> create(@Valid @RequestBody Booking booking) {
        this.bookingService.create(booking);
        LOGGER.info(I10001.getMessage(), booking.toString());
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Booking> delete(@PathVariable Long id) {
        this.bookingService.delete(id);
        LOGGER.info(I10002.getMessage(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<Booking> update(@Valid @RequestBody Booking booking, @PathVariable Long id) {
        Booking bookingUpdated = this.bookingService.update(booking, id);
        LOGGER.info(I10003.getMessage(), booking);
        return new ResponseEntity<>(bookingUpdated, HttpStatus.OK);
    }
}
