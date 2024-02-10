package com.app.booking.controller;

import com.app.booking.exception.Exception2HttpMapping;
import com.app.booking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
public class BookingControllerTest {
    public static final String BOOKING_ROOT = "/booking";
    private final BookingService mockBookingService;
    private final BookingController underTest;
    private final MockMvc mockMvc;

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
    }

}
