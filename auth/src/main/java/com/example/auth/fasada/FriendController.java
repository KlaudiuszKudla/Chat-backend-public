package com.example.auth.fasada;

import com.example.auth.entity.Code;
import com.example.auth.entity.FriendRequest;
import com.example.auth.entity.Response;
import com.example.auth.entity.UserChatDTO;
import com.example.auth.mediator.FriendMediator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friend")
@RequiredArgsConstructor
@Slf4j
public class FriendController {

    private final FriendMediator friendMediator;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserChatDTO>> getFriends(@RequestParam Long userId){
       return friendMediator.findFriends(userId);
    }

    @RequestMapping(value = "getFriendsRequest", method = RequestMethod.GET)
    public ResponseEntity<List<UserChatDTO>> getFriendsRequest(@RequestParam Long userId){
        return friendMediator.findFriendsRequest(userId);
    }

    @RequestMapping(value = "getFriendsSentRequest", method = RequestMethod.GET)
    public ResponseEntity<List<UserChatDTO>> getFriendsSentRequest(@RequestParam Long userId){
        return friendMediator.findFriendsSentRequest(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Response> addFriend(@RequestBody FriendRequest request){
        friendMediator.addFriend(request);
        return ResponseEntity.ok(new Response(Code.FRIEND_REQUEST_SENT));
    }

    @RequestMapping(value = "acceptFriend", method = RequestMethod.PATCH)
    public ResponseEntity<Response> acceptFriend(@RequestBody FriendRequest request){
        log.info("Start adding friend");
        friendMediator.acceptFriend(request);
        return ResponseEntity.ok(new Response(Code.FRIEND_REQUEST_ACCEPTED));
    }

    @RequestMapping(value = "blockFriend", method = RequestMethod.PATCH)
    public ResponseEntity<Response> blockFriend(@RequestBody FriendRequest request){
        friendMediator.blockFriend(request);
        return ResponseEntity.ok(new Response(Code.FRIEND_BLOCKED));
    }

    @RequestMapping(value = "deleteFriend",method = RequestMethod.POST)
    public ResponseEntity<Response> deleteFriend(@RequestBody FriendRequest request){
        friendMediator.deleteFriend(request);
        return ResponseEntity.ok(new Response(Code.FRIEND_DELETED));
    }

}
