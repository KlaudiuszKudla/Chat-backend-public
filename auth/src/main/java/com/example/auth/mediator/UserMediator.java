package com.example.auth.mediator;

import com.example.auth.entity.UserChatDTO;
import com.example.auth.services.UserService;
import com.example.auth.translator.UserToUserChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMediator {

    private final UserService userService;
    private final UserToUserChatDTO userToUserChatDTO;
    public ResponseEntity<List<UserChatDTO>> getUsers() {
        List<UserChatDTO> userChatDTOS = new ArrayList<>();
        userService.getUsers().forEach(value ->{
            userChatDTOS.add(userToUserChatDTO.toUserChatDTO(value));
        });
        return ResponseEntity.ok(userChatDTOS);
//        return ResponseEntity.ok(userChatService.getUsers());
    }

}
