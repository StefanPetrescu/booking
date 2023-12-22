package com.app.booking.repository;

import com.app.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b from Booking b where b.propertyName IN (:propertyName) AND ((:startDate >= b.startDate AND :startDate < b.endDate) " +
            "OR (:endDate > b.startDate AND :endDate <= b.endDate))")
    Optional<Booking> getBookingByPropertyNameAndDateBetweenExclusive(String propertyName, LocalDate startDate, LocalDate endDate);

    Optional<Booking> getBookingById(Long id);
}
