package com.vicariustest.repository;

import com.vicariustest.entity.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    void deleteById(String id);

    Optional<User> findById(String id);

}
