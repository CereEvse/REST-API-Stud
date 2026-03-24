package ru.rest_api_stud.dto.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
