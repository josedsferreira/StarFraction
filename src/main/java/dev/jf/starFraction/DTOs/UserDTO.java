package dev.jf.starFraction.DTOs;

import java.util.List;

import dev.jf.starFraction.models.Planet;
import dev.jf.starFraction.models.enums.UserRole;

public record UserDTO(Long userId, String email, String username, UserRole role, List<Planet> userPlanets) {

}
