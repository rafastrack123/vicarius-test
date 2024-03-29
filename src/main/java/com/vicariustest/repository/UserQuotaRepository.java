package com.vicariustest.repository;

import com.vicariustest.entity.UserQuota;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserQuotaRepository extends JpaRepository<UserQuota, String> {

    @Query("SELECT uq FROM UserQuota uq WHERE uq.user.id = :userId")
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<UserQuota> findByUserId(String userId);

}
