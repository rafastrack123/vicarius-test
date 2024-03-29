package com.vicariustest.repository.management;

import com.vicariustest.repository.UserElasticRepository;
import com.vicariustest.repository.UserRepository;
import com.vicariustest.repository.UserSqlRepository;
import com.vicariustest.service.DateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryManager {

    private final UserSqlRepository sqlRepository;
    private final UserElasticRepository elasticRepository;
    private final DateService dateService;

    @Value("${feature.toggle.elastic.repository.enabled}")
    private boolean elasticRepoEnabled;

    public UserRepository getRepository() {
        if(!elasticRepoEnabled){
            return sqlRepository;
        }

        var now = dateService.getCurrentTimeAtUTC();
        if (dateService.isDayTime(now)) {
            return sqlRepository;
        }

        return elasticRepository;
    }

}
