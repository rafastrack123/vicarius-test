package com.vicariustest.converter;

import com.vicariustest.entity.UserQuota;
import com.vicariustest.http.UserQuotaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserQuotaConverter {

    private final UserConverter userConverter;

    public List<UserQuotaResponse> from(List<UserQuota> quotas) {
        return quotas.stream()
                .map(this::from)
                .toList();
    }

    private UserQuotaResponse from(UserQuota quota) {
        var response = new UserQuotaResponse();

        response.setId(quota.getId());
        response.setCount(quota.getCount());
        response.setUser(userConverter.toResponse(quota.getUser()));

        return response;
    }
}
