package com.example.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangeUserData {

    private long id;
    private String login;
    private String password;
    private String imageUrl;

}
