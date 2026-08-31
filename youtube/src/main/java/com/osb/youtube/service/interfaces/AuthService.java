package com.osb.youtube.service.interfaces;

import com.osb.youtube.dto.request.LoginRequest;
import com.osb.youtube.dto.request.RegisterRequest;
import com.osb.youtube.dto.response.LoginResponse;

public interface AuthService {
    void register(RegisterRequest request);
    LoginResponse login(LoginRequest request);

}
