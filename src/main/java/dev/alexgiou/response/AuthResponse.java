package dev.alexgiou.response;

import dev.alexgiou.model.user.ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private ROLE role;
}
