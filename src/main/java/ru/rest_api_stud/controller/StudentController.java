package ru.rest_api_stud.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rest_api_stud.dto.request.StudentCreateRequest;
import ru.rest_api_stud.dto.request.StudentPatchRequest;
import ru.rest_api_stud.dto.request.StudentUpdateRequest;
import ru.rest_api_stud.dto.response.StudentResponse;
import ru.rest_api_stud.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentCreateRequest request) {
        StudentResponse created = studentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, @Valid @RequestBody StudentUpdateRequest request) {
        return ResponseEntity.ok(studentService.update(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponse> patch(@PathVariable Long id, @Valid @RequestBody StudentPatchRequest request) {
        return ResponseEntity.ok(studentService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
