package com.vicariustest.unittest.service;

import com.vicariustest.entity.User;
import com.vicariustest.entity.UserQuota;
import com.vicariustest.repository.UserQuotaRepository;
import com.vicariustest.service.UserQuotaService;
import com.vicariustest.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.thenCode;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@ExtendWith(MockitoExtension.class)
class UserQuotaServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private UserQuotaRepository repository;
    @InjectMocks
    private UserQuotaService service;

    @Test
    void shouldThrowTooManyRequestWhenQuotaReachedFive() {
        var user = mock(User.class);
        var quota = new UserQuota();

        quota.setUser(user);
        quota.setCount(5);

        given(repository.findByUserId("userId")).willReturn(Optional.of(quota));

        thenCode(() -> service.consumeQuota("userId"))
                .isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("status", TOO_MANY_REQUESTS);

        verify(userService, never()).getById(anyString());
    }

    @Test
    void shouldConsumeExistingQuota() {
        var user = mock(User.class);
        var quota = mock(UserQuota.class);

        given(quota.getCount()).willReturn(3L);
        given(quota.getUser()).willReturn(user);

        given(repository.findByUserId("userId")).willReturn(Optional.of(quota));

        service.consumeQuota("userId");

        verify(quota).increaseCount();
        verify(repository).save(quota);
        verify(userService).updateLastLoginTime(user);
        verify(userService, never()).getById(anyString());
    }

    @Test
    void shouldCreateNewQuotaForUser() {
        var user = mock(User.class);

        given(repository.findByUserId("userId")).willReturn(Optional.empty());
        given(userService.getById("userId")).willReturn(user);

        service.consumeQuota("userId");

        verify(repository).save(argThat(quota ->
                quota.getUser().equals(user) && quota.getCount() == 1)
        );
        verify(userService).updateLastLoginTime(user);
    }

}