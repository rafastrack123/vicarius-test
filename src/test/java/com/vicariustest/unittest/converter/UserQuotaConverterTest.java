package com.vicariustest.unittest.converter;

import com.vicariustest.converter.UserConverter;
import com.vicariustest.converter.UserQuotaConverter;
import com.vicariustest.entity.User;
import com.vicariustest.entity.UserQuota;
import com.vicariustest.http.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserQuotaConverterTest {

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserQuotaConverter converter;

    @Test
    void from() {
        var user = mock(User.class);
        var userResponse = mock(UserResponse.class);
        var quota = new UserQuota();

        quota.setId("id");
        quota.setCount(5);
        quota.setUser(user);

        given(userConverter.toResponse(quota.getUser())).willReturn(userResponse);

        var result = converter.from(List.of(quota));

        then(result).hasSize(1);
        var quotaResponse = result.get(0);

        then(quotaResponse.getId()).isEqualTo("id");
        then(quotaResponse.getCount()).isEqualTo(5);
        then(quotaResponse.getUser()).isEqualTo(userResponse);
    }
}