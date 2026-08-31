package com.osb.youtube.service.impl;

import com.osb.youtube.dto.request.LoginRequest;
import com.osb.youtube.dto.request.RegisterRequest;
import com.osb.youtube.dto.response.LoginResponse;
import com.osb.youtube.entity.Auth;
import com.osb.youtube.entity.Channel;
import com.osb.youtube.entity.User;
import com.osb.youtube.enums.Role;
import com.osb.youtube.repository.AuthRepository;
import com.osb.youtube.repository.ChannelRepository;
import com.osb.youtube.repository.UserRepository;
import com.osb.youtube.security.CustomUserDetails;
import com.osb.youtube.security.JwtService;
import com.osb.youtube.service.interfaces.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ChannelRepository channelRepository;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (authRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByUserEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setUserName(request.getUsername());
        user.setDisplayName(request.getDisplayName());
        user.setUserEmail(request.getEmail());
        userRepository.save(user);
        Channel channel = new Channel();
        channel.setChannelName(user.getDisplayName());
        channel.setDescription("");
        channel.setUser(user);
        channel.setProfileImageUrl("");
        channelRepository.save(channel);
        Auth auth = new Auth();
        auth.setUsername(request.getUsername());
        auth.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        auth.setRole(Role.USER);
        auth.setUser(user);
        authRepository.save(auth);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(),
                                    request.getPassword()
                            )
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails userDetails =
                    (CustomUserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);
            return LoginResponse.builder()
                    .token(token)
                    .build();
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
