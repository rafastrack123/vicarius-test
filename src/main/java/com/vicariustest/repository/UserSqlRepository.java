package com.vicariustest.repository;

import com.vicariustest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSqlRepository extends JpaRepository<User, String>, UserRepository {
}
