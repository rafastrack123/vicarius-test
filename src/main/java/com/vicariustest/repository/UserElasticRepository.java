package com.vicariustest.repository;

import com.vicariustest.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UserElasticRepository implements UserRepository {

    @Override
    public User save(User user) {
        log.info("Saving user on elastic repository");
        return new User();
    }

    @Override
    public void deleteById(String id) {
        log.info("Deleting user on elastic repository");
    }

    @Override
    public Optional<User> findById(String id) {
        log.info("Finding user on elastic repository");
        return Optional.empty();
    }

}
