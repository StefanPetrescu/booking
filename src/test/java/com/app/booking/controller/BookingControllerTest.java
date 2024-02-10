package com.app.booking.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.app.booking.exception.Exception2HttpMapping;
import com.app.booking.service.BookingService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class BookingControllerTest {
    public static final String BOOKING_ROOT = "/booking";
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(BookingController.class);
    private static final ListAppender<ILoggingEvent> LIST_APPENDER = new ListAppender<>();
    private final BookingService mockBookingService;
    private final BookingController underTest;
    private final MockMvc mockMvc;

    static {
        LOGGER.setLevel(Level.ALL);
        LIST_APPENDER.start();
        LOGGER.addAppender(LIST_APPENDER);
    }

    public BookingControllerTest() {
        this.mockBookingService = mock(BookingService.class);
        this.underTest = new BookingController(mockBookingService);
        mockMvc = standaloneSetup(underTest)
                .setControllerAdvice(new Exception2HttpMapping())
                .build();
    }

    @Test
    void delete_whenValidId_shouldRemoveFromDb() throws Exception {
        //given
        doNothing().when(mockBookingService).delete(1L);

        //when, then
        mockMvc.perform(
                        delete(BOOKING_ROOT + "/1")
                )
                .andExpect(status().isOk());
        List<ILoggingEvent> list = LIST_APPENDER.list;
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getMessage()).isEqualTo("Booking was successfully deletedBookingId=1 ]");
        assertThat(list.get(0).getLevel()).isEqualTo(Level.INFO);
    }

    @AfterEach
    void tearDown_afterEach() {
        LIST_APPENDER.list.clear();
    }

    @AfterAll
    static void tearDown_afterAll() {
        LOGGER.detachAppender(LIST_APPENDER);
        LIST_APPENDER.stop();
    }
}
