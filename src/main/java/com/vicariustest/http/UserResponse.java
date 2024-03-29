package com.vicariustest.http;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime lastLoginTimeUtc;

}
