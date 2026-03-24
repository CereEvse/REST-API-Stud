package ru.rest_api_stud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private String fullName;
    private String groupName;
    private String email;
    private Integer age;
    private Boolean active;
}
