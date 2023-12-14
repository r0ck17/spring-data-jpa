package by.javaguru.integration.service;

import by.javagur.spring.database.entity.Role;
import by.javagur.spring.database.entity.User;
import by.javagur.spring.database.repository.pool.ConnectionPool;
import by.javagur.spring.service.UserService;
import by.javaguru.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@IT
@RequiredArgsConstructor
public class UserServiceIT {

    private final UserService userService;

    @Test
    void findUserByRoleEqualsAndAndBirthDateBetween() {
        List<User> users = userService.findUserByRoleEqualsAndAndBirthDateBetween(Role.ADMIN, 1980, 1990);
        assertThat(users).hasSize(2);
    }
}