package com.example.websocket.controller;

import com.example.websocket.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat") // app/message
    @SendTo("/topic/messages")
    public String sendMessage(String message){
        return message;
    }

    @MessageMapping("/private-message") // app/private-message
    public Message receivePrivateMessage(@Payload Message message){
    simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);  // user/klaudiusz/private
        return message;
    }
}
