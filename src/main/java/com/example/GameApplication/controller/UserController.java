package com.example.GameApplication.controller;

import com.example.GameApplication.dto.UpdateLevelRequest;
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
    public ResponseEntity<User> createUser() {
        User user = userService.createUser();
        return ResponseEntity.ok(user);

    }

    @PostMapping("/updateLevel")
    public ResponseEntity<User> updateLevel(@RequestBody UpdateLevelRequest request) {
        User user = userService.updateLevel(request);
        return ResponseEntity.ok(user);
    }
}
