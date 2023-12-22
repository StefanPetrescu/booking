package com.app.booking.service;

import com.app.booking.model.Block;

import java.time.LocalDate;

public interface BlockService {
    void create(Block block);

    void delete(Long id);

    Block update(Block block, Long id);

    boolean checkBlockOverlap(String propertyName, LocalDate startDate, LocalDate endDate);
}
