package dev.jf.starFraction.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jf.starFraction.auth.infra.security.TokenService;
import dev.jf.starFraction.auth.users.AuthenticationDTO;
import dev.jf.starFraction.auth.users.LoginResponseDTO;
import dev.jf.starFraction.auth.users.RegisterDTO;
import dev.jf.starFraction.auth.users.User;
import dev.jf.starFraction.auth.users.UserRepository;
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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){

        //System.out.println(data);
        System.out.println("0-Login request received from username " + data.email());
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
            System.out.println("#-Login failed for username: " + data.email());
            System.out.println("Error: " + e);
            return ResponseEntity.badRequest().build();
        }
        
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.email(), encryptedPassword, data.username(), data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}