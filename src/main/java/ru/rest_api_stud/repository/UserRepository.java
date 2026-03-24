package ru.rest_api_stud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rest_api_stud.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
