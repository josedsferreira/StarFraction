package dev.jf.starFraction.DTOs;

import dev.jf.starFraction.Models.enums.UserRole;

public record RegisterDTO(String email , String password, String username, UserRole role) {

}
