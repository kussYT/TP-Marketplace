package com.insa.marketplace.mapper;

import com.insa.marketplace.dto.UserDto;
import com.insa.marketplace.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(UserDto dto);
}
