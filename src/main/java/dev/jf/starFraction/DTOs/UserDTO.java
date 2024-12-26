package dev.jf.starFraction.DTOs;

import java.util.List;

import dev.jf.starFraction.Models.Planet;
import dev.jf.starFraction.Models.enums.UserRole;

public record UserDTO(Long userId, String email, String username, UserRole role, List<Planet> userPlanets) {

}
