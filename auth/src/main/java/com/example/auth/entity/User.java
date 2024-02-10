package com.example.auth.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

    @Setter
    @Table(name = "users")
    @Entity
    public class User implements UserDetails {
        @Id
        @GeneratedValue(generator = "users_id_seq", strategy = GenerationType.SEQUENCE)
        @SequenceGenerator(name = "users_id_seq",sequenceName = "users_id_seq",allocationSize = 1)
        private long id;
        private String uuid;
        private String login;
        private String email;
        private String password;
        @Enumerated(EnumType.STRING)
        private Role role;
        private boolean isLocked;
        private boolean isEnabled;
        private String imageUuid;

        @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
        @JoinTable(
                name = "user_friends",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "friend_id")
        )
        private Set<User> friends = new HashSet<>();

    public User(){
        generateUuid();
    }
    public User(long id, String uuid, String login, String email, String password, Role role, boolean isLocked, boolean isEnabled) {
        this.id = id;
        this.uuid = uuid;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isLocked = isLocked;
        this.isEnabled = isEnabled;
        generateUuid();
    }

    public Role getRole(){
        return this.role;
    }
    public String getUuid(){
        return this.uuid;
    }
    public long getId(){
        return id;
    }
    public String getEmail() {
        return email;
    }
    public Set<User> getFriends() {
        return friends;
    }
    public String getImageUuid(){
        return imageUuid;
    }
    public boolean isLocked() {
            return isLocked;
        }

        @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }


    private void generateUuid(){
        if (uuid == null || uuid.equals("")){
            setUuid(UUID.randomUUID().toString());
        }
    }

    public void addFriend(User friend) {
        friends.add(friend);
        friend.getFriends().add(this);
    }

    public void removeFriend(User friend){
        friends.remove(friend);
        friend.getFriends().remove(this);
    }

}
