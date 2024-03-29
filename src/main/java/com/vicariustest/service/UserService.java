package com.vicariustest.service;

import com.vicariustest.entity.User;
import com.vicariustest.repository.UserRepository;
import com.vicariustest.repository.management.UserRepositoryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryManager repositoryManager;
    private final DateService dateService;

    public User getById(String id) {
        return getRepository().findById(id).orElseThrow();
    }

    public User save(User user) {
        return getRepository().save(user);
    }

    public void delete(String id) {
        getRepository().deleteById(id);
    }

    private UserRepository getRepository() {
        return repositoryManager.getRepository();
    }

    public void updateLastLoginTime(User user) {
        user.setLastLoginTimeUtc(dateService.getCurrentDateTimeAtUTC());
        getRepository().save(user);
    }
}
