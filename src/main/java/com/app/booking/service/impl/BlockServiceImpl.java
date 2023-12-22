package com.app.booking.service.impl;

import com.app.booking.exception.BlockException;
import com.app.booking.exception.BookingException;
import com.app.booking.model.Block;
import com.app.booking.model.Booking;
import com.app.booking.repository.BlockRepository;
import com.app.booking.service.BlockService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.app.booking.util.log.LoggingUtil.E50002;
import static com.app.booking.util.log.LoggingUtil.E50004;
import static com.app.booking.util.log.LoggingUtil.E50005;
import static com.app.booking.util.log.LoggingUtil.I10008;


@Service
public class BlockServiceImpl implements BlockService {
    private static final Logger LOGGER = LogManager.getLogger(BlockServiceImpl.class);
    private final BlockRepository blockRepository;
    private final BookingServiceImpl bookingService;

    //Based on my logic, I also assumed that a block cannot be created if a booking is already in place, that's why I decided to inject the bookingService in a lazy way.
    //In order to get rid of the circular references. (I know this is not a best practice, but I put the customer in the first place)
    //the other option was to check if there are any bookings, when I want to create a new block, and delete them, but (again) this will make the customer angry :D
    public BlockServiceImpl(BlockRepository blockRepository, @Lazy BookingServiceImpl bookingService) {
        this.blockRepository = blockRepository;
        this.bookingService = bookingService;
    }

    @Override
    public void create(Block block) {
        simulateBooking(block);
        this.blockRepository.save(block);
    }

    @Override
    public void delete(Long id) {
        Optional<Block> blockById = this.blockRepository.getBlockById(id);
        if (blockById.isPresent()) {
            this.blockRepository.deleteById(id);
        } else {
            LOGGER.error(E50004.getMessage(), id);
            throw new BlockException("Block was not found in the database");
        }
    }

    @Override
    public Block update(Block block, Long id) {
        Optional<Block> optionalBlockById = this.blockRepository.findById(id);
        if (optionalBlockById.isPresent()) {
            Block blockById = optionalBlockById.get();
            LOGGER.info(I10008.getMessage(), id);
            blockById.setId(id);
            blockById.setPropertyName(block.getPropertyName());
            blockById.setStartDate(block.getStartDate());
            blockById.setEndDate(block.getEndDate());
            simulateBooking(block);
            return this.blockRepository.save(blockById);
        } else {
            LOGGER.error(E50004.getMessage(), id);
            throw new BlockException("Block was not found in the database");
        }
    }

    @Override
    public boolean checkBlockOverlap(String propertyName, LocalDate startDate, LocalDate endDate) {
        Optional<Block> blockByPropertyNameAndStartDateBetween = blockRepository.getBlockByPropertyNameAndStartDateAndEndDateInclusive(propertyName, startDate, endDate);
        if (blockByPropertyNameAndStartDateBetween.isPresent()) {
            Block existingBlock = blockByPropertyNameAndStartDateBetween.get();
            LOGGER.error(E50002.getMessage(), existingBlock.getStartDate(), existingBlock.getEndDate());
            return true;
        }
        return false;
    }

    private void simulateBooking(Block block) {
        Booking booking = new Booking();
        booking.setPropertyName(block.getPropertyName());
        booking.setStartDate(block.getStartDate());
        booking.setEndDate(block.getEndDate());
        try {
            this.bookingService.checkBookingAlreadyExists(booking);
        } catch (BookingException e) {
            LOGGER.error(E50005.getMessage());
            throw new BlockException("You can't block the property between this dates because there is a booking registered");
        }
    }
}
