package dev.jf.starFraction.DTOs;

import dev.jf.starFraction.models.enums.UserRole;

public record LoginResponseDTO(String token, Long userId, String email, String username, UserRole role) {

}
