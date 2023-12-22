package com.app.booking.repository;

import com.app.booking.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    @Query("SELECT b from Block b where b.propertyName IN (:propertyName) AND ((:startDate >= b.startDate AND :startDate <= b.endDate) " +
            "OR (:endDate >= b.startDate AND :endDate <= b.endDate))")
    Optional<Block> getBlockByPropertyNameAndStartDateAndEndDateInclusive(String propertyName, LocalDate startDate, LocalDate endDate);

    Optional<Block> getBlockById(Long id);
}
