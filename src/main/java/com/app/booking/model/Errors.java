package com.app.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Errors {
    private String errorName;
    private String exceptionMessage;
}
