package by.javagur.spring.service;

import by.javagur.spring.database.entity.Company;
import by.javagur.spring.database.entity.Role;
import by.javagur.spring.database.entity.User;
import by.javagur.spring.database.repository.CompanyRepository;
import by.javagur.spring.database.repository.UserRepository;
import by.javagur.spring.dto.CompanyReadDto;
import by.javagur.spring.listener.AccessType;
import by.javagur.spring.listener.EntityEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id).map(entity -> {
            applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.DELETE));
            return new CompanyReadDto(entity.getId());
        });
    }

    public List<Company> deleteCompaniesByNameStartingWith(String start) {
        userRepository.disassociateUsersFromCompany(start);
        return companyRepository.deleteCompaniesByNameStartingWith(start);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}
