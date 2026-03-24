package ru.rest_api_stud.dto.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.rest_api_stud.dto.request.StudentCreateRequest;
import ru.rest_api_stud.dto.request.StudentPatchRequest;
import ru.rest_api_stud.dto.request.StudentUpdateRequest;
import ru.rest_api_stud.dto.response.StudentResponse;
import ru.rest_api_stud.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentCreateRequest dto);
    Student toEntity(StudentUpdateRequest dto);
    StudentResponse toResponse(Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromPatch(StudentPatchRequest patch, @MappingTarget Student student);
}
