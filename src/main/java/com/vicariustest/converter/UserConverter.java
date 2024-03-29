package com.vicariustest.converter;

import com.vicariustest.entity.User;
import com.vicariustest.http.UserRequest;
import com.vicariustest.http.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User toEntity(UserRequest request) {
        var user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return user;
    }

    public UserResponse toResponse(User user) {
        var userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setLastLoginTimeUtc(user.getLastLoginTimeUtc());

        return userResponse;
    }

    public User toUpdatedEntity(UserRequest request, User user) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return user;
    }
}
