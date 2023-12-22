package com.app.booking.util.log;

import lombok.Getter;

import java.util.stream.Stream;

import static com.app.booking.util.log.LoggingConstants.BLOCK;
import static com.app.booking.util.log.LoggingConstants.BLOCK_ID;
import static com.app.booking.util.log.LoggingConstants.BOOKING;
import static com.app.booking.util.log.LoggingConstants.BOOKING_ID;
import static com.app.booking.util.log.LoggingConstants.END_DATE;
import static com.app.booking.util.log.LoggingConstants.START_DATE;

@Getter
public enum LoggingUtil {
    I10001("Booking was successfully created", BOOKING),
    I10002("Booking was successfully deleted", BOOKING_ID),
    I10003("Booking was successfully updated", BOOKING),
    I10004("Booking was successfully created", BLOCK),
    I10005("Booking was successfully deleted", BLOCK_ID),
    I10006("Booking was successfully updated", BLOCK),
    I10007("Booking was successfully found in database", BOOKING_ID),
    I10008("Block was successfully found in database", BLOCK_ID),

    E50001("There is an overlapping with a booking", START_DATE, END_DATE),
    E50002("There is an overlapping with a block. The dates are INCLUSIVE.", START_DATE, END_DATE),
    E50003("Booking was not found in database", BOOKING_ID),
    E50004("Block was not found in database", BLOCK_ID),
    E50005("You can't block the property between this dates because there is a booking registered");

    private String message;

    LoggingUtil(String message, String... fields) {
        this.message = message;
        StringBuilder sb = new StringBuilder(message);
        Stream.of(fields).forEach(field -> sb.append(field).append("={}, "));
        sb.replace(sb.length() - 2, sb.length(), " ]");
        this.message = sb.toString();
    }
}
