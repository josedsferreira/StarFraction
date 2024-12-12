package dev.jf.starFraction.auth.users;

public record LoginResponseDTO(String token, Long userId, String username, UserRole role) {

}
