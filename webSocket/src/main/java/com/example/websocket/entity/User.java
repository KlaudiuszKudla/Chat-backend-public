package com.example.websocket.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class User {

    private long id;
    private String uuid;
    private String login;
    private String email;

    private String password;
    private String role;
    private boolean isLock;

    private boolean isEnabled;
}
