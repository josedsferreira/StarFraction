package dev.jf.starFraction.planets;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/planets")
public class PlanetController {

    private final PlanetService service;

    public PlanetController(PlanetService service) {
        this.service = service;
    }

    // GET
    @GetMapping
    public List<Planet> getAllPlanets() {
        return service.getAllPlanets();
    }

    // POST
    @PostMapping
    public ResponseEntity<Planet> savePlanet(@RequestBody Planet planet) {
        // create some filtering for the status
        Planet savedPlanet = service.savePlanet(planet);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlanet);
    }

    // FIND BY ID
    @GetMapping("/{id}")
    public Planet getPlanetById(@PathVariable Long id) {
        Optional<Planet> planet = service.getPlanetById(id);
        if (planet.isEmpty()) {
            throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Planet not found with ID " + id
            );
        }
        else return planet.get();
    }

    // FIND BY Name
    @GetMapping("/{name}")
    public Planet getPlanetByName(@PathVariable String name) {
        Optional<Planet> planet = service.getPlanetByName(name);
        if (planet.isEmpty()) {
            throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "No planet found with name " + name
            );
        }
        else return planet.get();
    }

    // FIND BY Size
    @GetMapping("/{size}")
    public Planet getPlanetBySize(@PathVariable PlanetSize size) {
        Optional<Planet> planet = service.getPlanetsBySize(size);
        if (planet.isEmpty()) {
            throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "No planet found with size " + size
            );
        }
        else return planet.get();
    }
    

}
