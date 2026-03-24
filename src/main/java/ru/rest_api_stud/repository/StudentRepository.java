package ru.rest_api_stud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rest_api_stud.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
}
