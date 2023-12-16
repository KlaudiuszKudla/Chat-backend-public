package com.example.auth.repository;

import com.example.auth.entity.Role;
import com.example.auth.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection =  EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_FindByLogin_ReturnTrue() {

        User user = new User();
        user.setLogin("user1234");
        user.setLock(true);
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

}
