package ru.rest_api_stud.service;

import ru.rest_api_stud.dto.request.StudentCreateRequest;
import ru.rest_api_stud.dto.request.StudentPatchRequest;
import ru.rest_api_stud.dto.request.StudentUpdateRequest;
import ru.rest_api_stud.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAll();
    StudentResponse getById(Long id);
    StudentResponse create(StudentCreateRequest request);
    StudentResponse update(Long id, StudentUpdateRequest request);
    StudentResponse patch(Long id, StudentPatchRequest request);
    void delete(Long id);
}