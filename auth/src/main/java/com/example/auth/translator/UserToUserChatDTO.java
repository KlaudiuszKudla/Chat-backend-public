package com.example.auth.translator;

import com.example.auth.entity.User;
import com.example.auth.entity.UserChatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.context.annotation.Bean;

@Mapper
public abstract class UserToUserChatDTO {

    public UserChatDTO toUserChatDTO(User user){
        return translateToUserChatDTO(user);
    }

    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "username")
    @Mapping(target = "email", source = "email")
    protected abstract UserChatDTO translateToUserChatDTO(User user);
}
