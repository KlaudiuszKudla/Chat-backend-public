package com.example.auth.repository;

import com.example.auth.entity.Role;
import com.example.auth.entity.User;
import com.example.auth.entity.UserRegisterDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection =  EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @DisplayName("UserRepository findUserByLoginAndIsLockedFalseAndIsEnabledTrueReturnNull test")
    @ParameterizedTest(name = "{0}")
    @MethodSource("findUserByLoginAndIsLockedFalseAndIsEnabledTrueData")
    void parametrizedTests(String login, boolean isLocked, boolean isEnabled){
        // Given
        User user = new User();
        user.setLogin(login);
        user.setLocked(isLocked);
        user.setEnabled(isEnabled);
        userRepository.save(user);
        // When
        Optional<User> actual = userRepository.findUserByLoginAndIsLockedFalseAndIsEnabledTrue(login);
        userRepository.delete(user);
        // then
        assertTrue(actual.isEmpty());
    }

    private static Stream<Arguments> findUserByLoginAndIsLockedFalseAndIsEnabledTrueData(){
        return Stream.of(
                Arguments.of("falseFalseUser", false, false),
                Arguments.of("TrueFalseUser", true, false),
                Arguments.of("TrueTrueUser", true, true)
        );
    }

    @Test
    public void UserRepository_findUserByLoginAndIsLockedFalseAndIsEnabledTrueReturnUser(){
        User user = new User();
        user.setLogin("login");
        user.setLocked(false);
        user.setEnabled(true);
        userRepository.save(user);
        // When
        Optional<User> actual = userRepository.findUserByLoginAndIsLockedFalseAndIsEnabledTrue(user.getUsername());
        userRepository.delete(user);
        // then
        assertTrue(actual.isPresent());
    }

    @Test
    public void UserRepository_FindByLogin_ReturnTrue() {

        User user = new User();
        user.setLogin("user1234");
        user.setLocked(true);
        user.setEnabled(false);
        user.setPassword("12345678");
        user.setEmail("test1234@gmail.com");
        user.setRole(Role.USER);

        userRepository.save(user);
        Optional<User> userReturn = userRepository.findUserByLogin(user.getUsername());
        Assertions.assertThat(userReturn).isNotNull();
    }

    @Test
    public void UserRepository_FindByLogin_ReturnFalse() {

        String nonexistentLogin = "nonexistentLogin";
        Optional<User> userReturn = userRepository.findUserByLogin(nonexistentLogin);
        assertTrue(userReturn.isEmpty(), "Expected empty Optional for nonexistent login");
    }

    @Test
    public void UserRepository_FindByMail_ReturnTrue() {
        User user = new User();
        user.setLogin("user1234");
        user.setLocked(true);
        user.setEnabled(false);
        user.setPassword("12345678");
        user.setEmail("test1234@gmail.com");
        user.setRole(Role.USER);
        userRepository.save(user);
        Optional<User> userReturn = userRepository.findUserByEmail(user.getEmail());
        Assertions.assertThat(userReturn).isNotNull();
    }

    @Test
    public void UserRepository_FindByMail_ReturnFalse() {
        String fakeEmail = "fakeEmail";
        Optional<User> userReturn = userRepository.findUserByEmail(fakeEmail);
        assertTrue(userReturn.isEmpty(), "Expected empty Optional for fake login");
    }

}
