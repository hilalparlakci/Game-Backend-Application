package com.example.GameApplication.adapter.in.web.controller;

import com.example.GameApplication.application.domain.dto.response.CreateUserResponse;
import com.example.GameApplication.application.domain.dto.request.UpdateLevelRequest;
import com.example.GameApplication.application.domain.dto.response.UpdateLevelResponse;
import com.example.GameApplication.application.domain.model.User;
import com.example.GameApplication.application.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser() {
        User user = userService.createUser();
        CreateUserResponse response = new CreateUserResponse(
                user.getId(),
                user.getLevel(),
                user.getCoin(),
                user.getCountry().getName()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateLevel")
    public ResponseEntity<UpdateLevelResponse> updateLevel(@RequestBody UpdateLevelRequest request) {
        User user = userService.updateLevel(request);
        UpdateLevelResponse response = new UpdateLevelResponse(
                user.getId(),
                user.getLevel(),
                user.getCoin(),
                user.getCountry().getName()
        );
        return ResponseEntity.ok(response);
    }
}
