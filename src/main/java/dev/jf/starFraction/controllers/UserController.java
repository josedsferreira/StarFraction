package dev.jf.starFraction.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.jf.starFraction.DTOs.UserDTO;
import dev.jf.starFraction.models.Planet;
import dev.jf.starFraction.models.User;
import dev.jf.starFraction.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // GET ALL USERS
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> userList = service.getAllUsers();
        List<UserDTO> userDTOList = userList.stream()
                .map(user -> new UserDTO(user.getUserId(), user.getEmail(), user.getUsername(), user.getRole(), user.getUserPlanets()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOList);
    }

    // GET BY EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<User> user = service.getUserByEmail(email);
        if (user.isEmpty()) {
            throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "No user found with email " + email
            );
        }
        else {
            UserDTO userDTO = new UserDTO(user.get().getUserId(), user.get().getEmail(), user.get().getUsername(), user.get().getRole(), user.get().getUserPlanets());
            return ResponseEntity.ok(userDTO);
        }
    }

    // GET BY USERNAME
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        Optional<User> user = service.getUserByUsername(username);
        if (user.isEmpty()) {
            throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "No user found with username " + username
            );
        }
        else {
            UserDTO userDTO = new UserDTO(user.get().getUserId(), user.get().getEmail(), user.get().getUsername(), user.get().getRole(), user.get().getUserPlanets());
            return ResponseEntity.ok(userDTO);
        }
    }

    // GET BY ID
    @GetMapping("/id/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = service.getUserById(id);
        if (user == null) {
            throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "No user found with ID " + id
            );
        }
        else {
            UserDTO userDTO = new UserDTO(user.getUserId(), user.getEmail(), user.getUsername(), user.getRole(), user.getUserPlanets());
            return ResponseEntity.ok(userDTO);
        }
    }

    // GET PLANETS BY USER ID
    @GetMapping("/userplanets/{id}")
    public ResponseEntity<List<Planet>> getPlanetsByUserId(@PathVariable Long id) {
        List<Planet> planetList = service.getPlanetsByUserId(id);
        
        if (planetList == null) {
            throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "No planets found for user with ID " + id
            );
        }
        else return ResponseEntity.ok(planetList);
    }
    
}
