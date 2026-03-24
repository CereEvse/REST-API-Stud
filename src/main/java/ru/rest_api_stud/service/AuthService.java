package ru.rest_api_stud.service;

import ru.rest_api_stud.dto.request.AuthRequest;
import ru.rest_api_stud.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);
}
