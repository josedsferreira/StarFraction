package dev.jf.starFraction.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jf.starFraction.DTOs.AuthenticationDTO;
import dev.jf.starFraction.DTOs.LoginResponseDTO;
import dev.jf.starFraction.DTOs.RegisterDTO;
import dev.jf.starFraction.Models.Planet;
import dev.jf.starFraction.Models.User;
import dev.jf.starFraction.auth.infra.security.TokenService;
import dev.jf.starFraction.repositories.UserRepository;
import dev.jf.starFraction.services.PlanetService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PlanetService planetService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){

        java.time.LocalTime time = java.time.LocalTime.now();
        System.out.println("0-Login request from user " + data.email() + " at: " + time);
        /* System.out.println("Full data: ");
        System.out.println(data); */

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            //System.out.println("1-UsernamePassword step done: " + usernamePassword);
    
            var auth = this.authenticationManager.authenticate(usernamePassword);
            //System.out.println("2-auth step done: " + auth);
    
            var user = (User) auth.getPrincipal(); // get the user from database
            
            var token = tokenService.generateToken(user);
            //System.out.println("3-token generated: " + token);
    
            //System.out.println("4-Login successful for username: " + data.username());
    
            return ResponseEntity.ok(new LoginResponseDTO(token, user.getUserId(), user.getEmail(), user.getUsername(), user.getRole()));
        }
        catch (Exception e) {
            System.out.println("#-Login failed for user: " + data.email());
            System.out.println("Error: " + e);
            return ResponseEntity.badRequest().build();
        }
        
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.email(), encryptedPassword, data.username(), data.role());

        User savedUser = this.repository.save(newUser);

        // Create a planet for the new user
        Planet newPlanet = new Planet();
        newPlanet.setUserId(savedUser.getUserId());
        newPlanet.setPlanetName("New Colony");
        planetService.savePlanet(newPlanet);

        return ResponseEntity.ok().build();
    }
}
