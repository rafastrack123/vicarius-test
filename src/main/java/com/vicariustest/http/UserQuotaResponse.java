package com.vicariustest.http;

import lombok.Data;

@Data
public class UserQuotaResponse {

    private String id;
    private long count;
    private UserResponse user;
}
