package com.example.auth.fasada;

import com.example.auth.entity.UserChatDTO;
import com.example.auth.mediator.UserMediator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserMediator userMediator;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserChatDTO>> getUsers(){
        return userMediator.getUsers();
    }

}
