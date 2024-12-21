package dev.jf.starFraction.auth.users;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.jf.starFraction.planets.Planet;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public Optional<User> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public User getUserById (Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        else return null;
    }

    public List<Planet> getPlanetsByUserId(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            return user.get().getUserPlanets();
        }
        else return null;
    }

}
