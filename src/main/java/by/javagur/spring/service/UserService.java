package by.javagur.spring.service;

import by.javagur.spring.database.entity.Role;
import by.javagur.spring.database.entity.User;
import by.javagur.spring.database.repository.UserRepository;
import by.javagur.spring.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@ToString
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public List<User> findUserByRoleEqualsAndAndBirthDateBetween(Role role, Integer yearFrom, Integer yearTo) {
        return userRepository.findUsersByRoleEqualsAndAndBirthDateBetween(role, yearFrom, yearTo);
    }
}
