package com.vicariustest.unittest.management;

import com.vicariustest.repository.UserElasticRepository;
import com.vicariustest.repository.UserSqlRepository;
import com.vicariustest.repository.management.UserRepositoryManager;
import com.vicariustest.service.DateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalTime;

import static java.time.LocalTime.now;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class UserRepositoryManagerTest {

    @Mock
    private UserSqlRepository sqlRepository;

    @Mock
    private UserElasticRepository elasticRepository;

    @Mock
    private DateService dateService;

    @InjectMocks
    private UserRepositoryManager userRepositoryManager;

    private final LocalTime NOW = now();

    @Test
    public void shouldReturnSqlRepoWhenIsDaylight() {
        ReflectionTestUtils.setField(userRepositoryManager, "elasticRepoEnabled", true);

        given(dateService.getCurrentTimeAtUTC()).willReturn(NOW);
        given(dateService.isDayTime(NOW)).willReturn(true);

        var result = userRepositoryManager.getRepository();

        then(result).isEqualTo(sqlRepository);
    }

    @Test
    public void shouldReturnElasticRepoWhenIsNight() {
        ReflectionTestUtils.setField(userRepositoryManager, "elasticRepoEnabled", true);

        given(dateService.getCurrentTimeAtUTC()).willReturn(NOW);
        given(dateService.isDayTime(NOW)).willReturn(false);

        var result = userRepositoryManager.getRepository();

        then(result).isEqualTo(elasticRepository);
    }

    @Test
    public void shouldAlwaysReturnSqlWhenFeatureFlagIsDisabled() {
        ReflectionTestUtils.setField(userRepositoryManager, "elasticRepoEnabled", false);

        var result = userRepositoryManager.getRepository();

        then(result).isEqualTo(sqlRepository);

        verifyNoInteractions(dateService);
    }
}