package com.example.GameApplication.service;

import com.example.GameApplication.dto.UpdateLevelRequest;
import com.example.GameApplication.model.User;

public interface UserService {
    User createUser();
    User updateLevel(UpdateLevelRequest request);
}
