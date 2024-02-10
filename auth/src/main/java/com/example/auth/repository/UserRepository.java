package com.example.auth.repository;

import com.example.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByLogin(String login);
    @Query(nativeQuery = true, value = "SELECT * FROM users where login=?1 and is_locked=false and is_enabled=true")
    Optional<User> findUserByLoginAndIsLockedFalseAndIsEnabledTrue(String login);
    @Query(nativeQuery = true, value = "SELECT * FROM users where login=?1 and is_locked=false and is_enabled=true and role='ADMIN'")
    Optional<User> findUserByLoginAndLockAndEnabledAndIsAdmin(String login);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUuid(String uuid);
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_friends SET is_accepted = true WHERE user_id=?1 AND friend_id=?2")
    void acceptFriendship(Long userId, Long friendID);
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_friends SET is_blocked = true WHERE user_id=?1 AND friend_id=?2")
    void blockFriend(Long userId, Long friendId);

//    @Query(nativeQuery = true, value = "SELECT u.* FROM users u  JOIN user_friends uf ON u.id = uf.friend_id WHERE uf.friend_id=?1 AND isAccepted=true")
@Query(nativeQuery = true, value = "SELECT u.* FROM users u " +
        "JOIN user_friends uf1 ON u.id = uf1.friend_id " +
        "JOIN user_friends uf2 ON u.id = uf2.user_id " +
        "WHERE (uf1.user_id = ?1 AND uf1.isAccepted = true) " +
        "AND (uf2.friend_id = ?1 AND uf2.isAccepted = true)")

    Optional<List<User>> findFriendsByUserId(Long id);

    @Query(nativeQuery = true, value = "SELECT u.* FROM users u  JOIN user_friends uf ON u.id = uf.friend_id WHERE uf.user_id=?1  AND is_accepted=false")
    Optional<List<User>> findFriendsByIdAndIsAcceptedByUserFalse(Long userId);

    @Query(nativeQuery = true, value = "SELECT u.* FROM users u  JOIN user_friends uf ON u.id = uf.user_id WHERE uf.friend_id=?1  AND is_accepted=false")
    Optional<List<User>> findFriendsByIdAndIsAcceptedByFriendFalse(Long id);
}
