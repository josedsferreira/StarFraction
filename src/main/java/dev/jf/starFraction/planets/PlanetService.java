package dev.jf.starFraction.planets;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PlanetService {

    private final PlanetRepository repository;

    public PlanetService(PlanetRepository repository) {
        this.repository = repository;
    }

    public Optional<Planet> getPlanetById(Long id) {
        return repository.findById(id);
    }

    public Planet savePlanet(Planet planet) {
        return repository.save(planet);
    }

    public void deletePlanet(Long id) {
        repository.deleteById(id);
    }

    public List<Planet> getAllPlanets() {
        return repository.findAll();
    }

    public Optional<Planet> getPlanetByName(String name) {
        return repository.findByPlanetName(name);
    }

    public Optional<Planet> getPlanetsBySize(PlanetSize size) {
        return repository.findByPlanetSize(size);
    }
}
