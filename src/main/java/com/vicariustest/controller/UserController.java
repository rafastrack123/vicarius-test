package com.vicariustest.controller;


import com.vicariustest.converter.UserConverter;
import com.vicariustest.converter.UserQuotaConverter;
import com.vicariustest.http.UserQuotaResponse;
import com.vicariustest.http.UserRequest;
import com.vicariustest.http.UserResponse;
import com.vicariustest.service.UserQuotaService;
import com.vicariustest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserConverter converter;
    private final UserQuotaService quotaService;
    private final UserQuotaConverter quotaConverter;

    @PostMapping
    @ResponseStatus(CREATED)
    public UserResponse createUser(@RequestBody UserRequest user) {
        var created = converter.toEntity(user);

        var saved = service.save(created);

        return converter.toResponse(saved);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId) {
        var user = service.getById(userId);

        return converter.toResponse(user);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @RequestBody UserRequest updateRequest) {
        var oldUser = service.getById(userId);

        var updatedUser = converter.toUpdatedEntity(updateRequest, oldUser);

        var saved = service.save(updatedUser);

        return converter.toResponse(saved);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        service.delete(userId);
    }

    @PostMapping("/{userId}/quotas/consume")
    public void consumeQuota(@PathVariable String userId) {
        quotaService.consumeQuota(userId);
    }

    @GetMapping("/quotas")
    public List<UserQuotaResponse> getUsersQuota() {
        return quotaConverter.from(quotaService.findAll());
    }
}
