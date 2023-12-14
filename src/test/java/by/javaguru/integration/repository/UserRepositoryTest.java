package by.javaguru.integration.repository;

import by.javagur.spring.database.entity.Company;
import by.javagur.spring.database.entity.Role;
import by.javagur.spring.database.entity.User;
import by.javagur.spring.database.repository.UserRepository;
import by.javagur.spring.dto.PersonalInfo;
import by.javaguru.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
public class UserRepositoryTest {
    private final UserRepository userRepository;

    @Test
    void checkProjections() {
        var users = userRepository.findAllByCompanyId(1);
        assertThat(users).hasSize(2);
    }

    @Test
    void checkPageable() {
        var pageable = PageRequest.of(1, 2, Sort.by("id"));
        var page = userRepository.findAllBy(pageable);
        page.forEach(u -> System.out.println(u.getId()));
        while (page.hasNext()) {
            page = userRepository.findAllBy(page.nextPageable());
            page.forEach(u -> System.out.println(u.getId()));
            System.out.println(page.getTotalPages());
        }

    }


    @Test
    void findFirst3By() {
        var users = userRepository.findFirst3By(Sort.by("firstname")
                .and(Sort.by("firstname")).descending());
        assertTrue(!users.isEmpty());
        assertThat(users).hasSize(3);

    }

    @Test
    void updateRoleTest() {
        var entity1 = userRepository.findById(1L);
        assertEquals(Role.ADMIN, entity1.get().getRole());
        var result = userRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(2, result);
        var entity2 = userRepository.findById(1L);
        assertEquals(Role.USER, entity2.get().getRole());
    }

    @Test
    void findAllByFirstnameContainingAndLastnameContainingTest() {
        var user = userRepository.findAllByFirstnameContainingAndLastnameContaining("a", "a");
        assertFalse(user.isEmpty());
        assertThat(user).hasSize(3);
    }
}