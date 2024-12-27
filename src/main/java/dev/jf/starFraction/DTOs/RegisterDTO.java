package dev.jf.starFraction.DTOs;

import dev.jf.starFraction.models.enums.UserRole;

public record RegisterDTO(String email , String password, String username, UserRole role) {

}
