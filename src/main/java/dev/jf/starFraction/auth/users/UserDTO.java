package dev.jf.starFraction.auth.users;

import java.util.List;

import dev.jf.starFraction.planets.Planet;

public record UserDTO(Long userId, String email, String username, UserRole role, List<Planet> userPlanets) {

}
