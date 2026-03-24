package ru.rest_api_stud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "group_name")
    private String groupName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private Boolean active;
}
