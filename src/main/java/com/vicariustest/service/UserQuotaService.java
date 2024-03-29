package com.vicariustest.service;

import com.vicariustest.entity.UserQuota;
import com.vicariustest.repository.UserQuotaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserQuotaService {

    private final UserService userService;
    private final UserQuotaRepository repository;

    private final int MAX_REQUEST_LIMIT = 5;

    public void consumeQuota(String userId) {
        var quota = repository.findByUserId(userId)
                .orElseGet(() -> createNewQuota(userId));

        if (quota.getCount() >= MAX_REQUEST_LIMIT) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, String.format("User %s reached quota", userId));
        }

        quota.increaseCount();

        repository.save(quota);
        userService.updateLastLoginTime(quota.getUser());
    }

    private UserQuota createNewQuota(String userId) {
        var user = userService.getById(userId);

        var newQuota = new UserQuota();

        newQuota.setUser(user);

        return newQuota;
    }

    public List<UserQuota> findAll() {
        return repository.findAll();
    }
}
