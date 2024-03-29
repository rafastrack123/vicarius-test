package com.vicariustest.unittest.service;

import com.vicariustest.entity.User;
import com.vicariustest.repository.UserRepository;
import com.vicariustest.repository.management.UserRepositoryManager;
import com.vicariustest.service.DateService;
import com.vicariustest.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryManager repositoryManager;
    @Mock
    private DateService dateService;

    @InjectMocks
    private UserService service;

    @Test
    void updateLastLoginTime() {
        var user = mock(User.class);
        var now = LocalDateTime.now();
        var repository = mock(UserRepository.class);

        given(dateService.getCurrentDateTimeAtUTC()).willReturn(now);
        given(repositoryManager.getRepository()).willReturn(repository);

        service.updateLastLoginTime(user);

        verify(user).setLastLoginTimeUtc(now);
        verify(repository).save(user);
    }
}