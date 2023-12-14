package by.javagur.spring.database.repository;

import by.javagur.spring.database.entity.Role;
import by.javagur.spring.database.entity.User;
import by.javagur.spring.dto.IPersonalInfo;
import by.javagur.spring.dto.PersonalInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u.firstname, u.lastname, u.birth_date birthDate from users u " +
            "where u.company_id = :companyId",
    nativeQuery = true)
    List<IPersonalInfo> findAllByCompanyId(Integer companyId);

    Page<User> findAllBy(Pageable pageable);

    List<User> findFirst3By(Sort sort);

    @Query("select u from User u " +
            "where u.firstname like %:firstname% and u.lastname like %:lastname%")
    List<User> findAllByFirstnameContainingAndLastnameContaining(String firstname, String lastname);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.role = :role " +
            "where u.id in (:ids)")
    int updateRole(Role role, Long... ids);

    @Modifying
    @Query("update User u set u.company = null where u.company.id in " +
           "(select c.id from Company c where c.name like :start)")
    void disassociateUsersFromCompany(@Param("start") String start);

    @Query("from User u where u.role = :role and year(u.birthDate) between :yearFrom and :yearTo")
    List<User> findUsersByRoleEqualsAndAndBirthDateBetween(Role role, Integer yearFrom, Integer yearTo);
}
