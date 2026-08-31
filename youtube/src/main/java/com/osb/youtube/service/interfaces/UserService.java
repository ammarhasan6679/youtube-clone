package com.osb.youtube.service.interfaces;

import com.osb.youtube.dto.request.UpdateUserRequest;
import com.osb.youtube.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserById(String id);

    UserResponse getUserByUsername(String username);

    UserResponse updateProfile(UpdateUserRequest request);

    UserResponse getCurrentUser();

}
