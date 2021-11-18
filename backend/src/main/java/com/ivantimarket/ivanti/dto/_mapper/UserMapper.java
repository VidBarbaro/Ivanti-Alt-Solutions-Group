package com.ivantimarket.ivanti.dto._mapper;

import com.ivantimarket.ivanti.dto.UserAuthDTO;
import com.ivantimarket.ivanti.dto.NewUserDTO;
import com.ivantimarket.ivanti.dto.UserDTO;
import com.ivantimarket.ivanti.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDto(User user);
    User toUser(NewUserDTO user);
    User toUserDTO(UserDTO user);
    List<UserDTO> toUserDTOs(List<User> users);
    User fakeDTO(UserDTO user);
    UserAuthDTO toUserAuthDTO(User user);
}
