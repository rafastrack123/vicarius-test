package com.vicariustest.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.ZoneOffset.UTC;

@Service
public class DateService {

    private static final LocalTime START_WORKING_HOUR = LocalTime.of(9, 0);
    private static final LocalTime END_WORKING_HOUR = LocalTime.of(17, 0);

    public boolean isDayTime(LocalTime time) {
        return !time.isBefore(START_WORKING_HOUR) && !time.isAfter(END_WORKING_HOUR);

    }

    public LocalTime getCurrentTimeAtUTC() {
        return getCurrentDateTimeAtUTC().toLocalTime();
    }

    public LocalDateTime getCurrentDateTimeAtUTC() {
        return LocalDateTime.now(UTC);
    }
}
