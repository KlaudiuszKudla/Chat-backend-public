package com.example.auth.services;

import com.example.auth.entity.FriendRequest;
import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {

    private final UserRepository userRepository;

    public List<User> getFriends(Long id) {
        return userRepository.findFriendsByUserId(id).orElse(null);
    }
    public  List<User> getFriendsRequest(Long id) {
        return userRepository.findFriendsByIdAndIsAcceptedByUserFalse(id).orElse(null);
    }

    public List<User> getFriendsSentRequest(Long id) {
        return userRepository.findFriendsByIdAndIsAcceptedByFriendFalse(id).orElse(null);
    }

    public void addFriend(FriendRequest request) {
        User user = userRepository.findUserById(request.getUserId()).orElse(null);
        User friend = userRepository.findUserById(request.getFriendId()).orElse(null);
        if(user != null && friend != null){
            user.addFriend(friend);
            userRepository.saveAndFlush(user);
        }
    }

    @Transactional
    public void acceptFriend(FriendRequest request) {
            userRepository.acceptFriendship(request.getUserId(), request.getFriendId());
    }

    @Transactional
    public void blockFriend(FriendRequest request) {
            userRepository.blockFriend(request.getUserId(), request.getFriendId());
    }


    public void deteteFriend(FriendRequest request) {
        User user = userRepository.findUserById(request.getUserId()).orElse(null);
        User friend = userRepository.findUserById(request.getFriendId()).orElse(null);
        if(user != null && friend != null){
            user.removeFriend(friend);
        }
        userRepository.saveAndFlush(user);
    }



}
