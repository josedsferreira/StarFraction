package dev.jf.starFraction.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.jf.starFraction.DTOs.BuildingDetailsDTO;
import dev.jf.starFraction.models.Planet;
import dev.jf.starFraction.models.User;
import dev.jf.starFraction.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    @Autowired
    private final PlanetService planetService;

    public UserService(UserRepository repository, PlanetService planetService) {
        this.repository = repository;
        this.planetService = planetService;
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
            List<Planet> planetList = user.get().getUserPlanets();
            for (Planet planet : planetList) {
                planetService.updateResources(planet.getPlanetId());
            }
            return planetList;
        }
        else return null;
    }

    public List<BuildingDetailsDTO> getPlanetsWithDetailsByUserId(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            List<Planet> planetList = user.get().getUserPlanets();
            List<BuildingDetailsDTO> planetDetailsList = new ArrayList<>();
            for (Planet planet : planetList) {
                planetService.updateResources(planet.getPlanetId());
                planetDetailsList.add(planetService.getPlanetBuildingDetails(planet.getPlanetId()));
            }
            return planetDetailsList;
        }
        else return null;
    }


}
