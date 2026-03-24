package ru.rest_api_stud.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rest_api_stud.dto.mapper.StudentMapper;
import ru.rest_api_stud.dto.request.StudentCreateRequest;
import ru.rest_api_stud.dto.request.StudentPatchRequest;
import ru.rest_api_stud.dto.request.StudentUpdateRequest;
import ru.rest_api_stud.dto.response.StudentResponse;
import ru.rest_api_stud.entity.Student;
import ru.rest_api_stud.exception.BusinessValidationException;
import ru.rest_api_stud.exception.ResourceNotFoundException;
import ru.rest_api_stud.repository.StudentRepository;
import ru.rest_api_stud.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<StudentResponse> getAll() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    public StudentResponse getById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return studentMapper.toResponse(student);
    }
    @Override
    public StudentResponse create(StudentCreateRequest request) {
        // Бизнес-валидация: уникальность email
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new BusinessValidationException("Email already exists: " + request.getEmail());
        }
        Student student = studentMapper.toEntity(request);
        student = studentRepository.save(student);
        return studentMapper.toResponse(student);
    }
    @Override
    public StudentResponse update(Long id, StudentUpdateRequest request) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        // Если email изменён, проверяем уникальность
        if (!existing.getEmail().equals(request.getEmail()) && studentRepository.existsByEmail(request.getEmail())) {
            throw new BusinessValidationException("Email already exists: " + request.getEmail());
        }
        // Полное обновление
        Student updated = studentMapper.toEntity(request);
        updated.setId(id);
        updated = studentRepository.save(updated);
        return studentMapper.toResponse(updated);
    }
    @Override
    public StudentResponse patch(Long id, StudentPatchRequest request) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        // Если email передан и изменён, проверяем уникальность
        if (request.getEmail() != null && !existing.getEmail().equals(request.getEmail())
                && studentRepository.existsByEmail(request.getEmail())) {
            throw new BusinessValidationException("Email already exists: " + request.getEmail());
        }
        // Частичное обновление: копируем только переданные поля
        studentMapper.updateStudentFromPatch(request, existing);
        existing = studentRepository.save(existing);
        return studentMapper.toResponse(existing);
    }
    @Override
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}