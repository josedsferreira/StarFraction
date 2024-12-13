package dev.jf.starFraction.auth.users;

public record RegisterDTO(String email , String password, String username, UserRole role) {

}
