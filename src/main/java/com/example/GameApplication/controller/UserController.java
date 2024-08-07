package com.example.GameApplication.controller;

import com.example.GameApplication.dto.CreateUserResponse;
import com.example.GameApplication.dto.UpdateLevelRequest;
import com.example.GameApplication.dto.UpdateLevelResponse;
import com.example.GameApplication.model.User;
import com.example.GameApplication.service.UserService;
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
