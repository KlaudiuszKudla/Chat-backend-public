package com.example.websocket.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {

    private String senderName;
    private String receiverName;
    private String message;

}
