package com.vicariustest.unittest.service;

import com.vicariustest.service.DateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DateServiceTest {

    @InjectMocks
    private DateService service;

    @Test
    void isDayTime() {

        then(service.isDayTime( LocalTime.of(9, 0))).isEqualTo(true);
        then(service.isDayTime( LocalTime.of(17, 0))).isEqualTo(true);
        then(service.isDayTime( LocalTime.of(12, 0))).isEqualTo(true);
        then(service.isDayTime( LocalTime.of(8, 59))).isEqualTo(false);
        then(service.isDayTime( LocalTime.of(17, 1))).isEqualTo(false);
        then(service.isDayTime( LocalTime.of(4, 0))).isEqualTo(false);
    }
}