package com.vicariustest.unittest.entity;

import com.vicariustest.entity.UserQuota;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class UserQuotaTest {

    @Test
    void increaseCount() {
        var quota = new UserQuota();

        quota.setCount(1);

        quota.increaseCount();

        then(quota.getCount()).isEqualTo(2);
    }
}