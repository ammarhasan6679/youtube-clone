package com.osb.youtube.mapper;

import com.osb.youtube.dto.request.RegisterRequest;
import com.osb.youtube.dto.response.UserResponse;
import com.osb.youtube.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userName", target = "username")
    @Mapping(source = "userEmail", target = "email")
    UserResponse toUserResponse(User user);

    @Mapping(source = "username", target = "userName")
    @Mapping(source = "email", target = "userEmail")
    User toEntity(RegisterRequest request);
}
