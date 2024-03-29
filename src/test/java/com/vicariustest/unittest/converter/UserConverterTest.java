package com.vicariustest.unittest.converter;

import com.vicariustest.converter.UserConverter;
import com.vicariustest.entity.User;
import com.vicariustest.http.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
class UserConverterTest {

    @InjectMocks
    private UserConverter converter;

    @Test
    void toEntity() {
        var request = new UserRequest();

        request.setFirstName("first");
        request.setLastName("last");

        var result = converter.toEntity(request);

        then(result.getId()).isNull();
        then(result.getFirstName()).isEqualTo("first");
        then(result.getLastName()).isEqualTo("last");
    }

    @Test
    void toResponse() {
        var user = new User();
        var now = LocalDateTime.now();

        user.setId("id");
        user.setFirstName("first");
        user.setLastName("last");
        user.setLastLoginTimeUtc(now);

        var result = converter.toResponse(user);

        then(result.getId()).isEqualTo("id");
        then(result.getFirstName()).isEqualTo("first");
        then(result.getLastName()).isEqualTo("last");
        then(result.getLastLoginTimeUtc()).isEqualTo(now);
    }

    @Test
    void toUpdatedEntity() {
        var request = new UserRequest();
        var user = new User();
        var now = LocalDateTime.now();

        request.setFirstName("updated-first");
        request.setLastName("updated-last");

        user.setId("id");
        user.setFirstName("first");
        user.setLastName("last");
        user.setLastLoginTimeUtc(now);

        var result = converter.toUpdatedEntity(request, user);

        then(result.getId()).isEqualTo("id");
        then(result.getFirstName()).isEqualTo("updated-first");
        then(result.getLastName()).isEqualTo("updated-last");
        then(result.getLastLoginTimeUtc()).isEqualTo(now);
    }
}