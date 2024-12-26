package dev.jf.starFraction.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.jf.starFraction.Models.Planet;
import dev.jf.starFraction.Models.enums.PlanetSize;
import dev.jf.starFraction.services.PlanetService;

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
    public ResponseEntity<List<Planet>> getAllPlanets() {
        List<Planet> planets = service.getAllPlanets();
        return ResponseEntity.ok(planets);
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
    public ResponseEntity<Planet> getPlanetById(@PathVariable Long id) {
        Optional<Planet> planet = service.getPlanetById(id);
        if (planet.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Planet not found with ID " + id
            );
        }
        return ResponseEntity.ok(planet.get());
    }

    // FIND BY Name
    @GetMapping("/{name}")
    public ResponseEntity<List<Planet>> getPlanetByName(@PathVariable String name) {
        List<Planet> planetList = service.getPlanetByName(name);
        if (planetList.isEmpty()) {
            throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "No planet found with name " + name
            );
        }
        else return ResponseEntity.ok(planetList);
    }

    // FIND BY Size
    @GetMapping("/{size}")
    public ResponseEntity<List<Planet>> getPlanetBySize(@PathVariable PlanetSize size) {
        List<Planet> planetList = service.getPlanetsBySize(size);
        if (planetList.isEmpty()) {
            throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "No planet found with size " + size
            );
        }
        else return ResponseEntity.ok(planetList);
    }
    
    // UPGRADE RESOURCE BUILDING
    @PostMapping("/{id}/upgradeResourceBuilding/{buildingType}")
    public ResponseEntity<Planet> upgradeResourceBuilding(@PathVariable Long id, @PathVariable String buildingType) {
        try {
            service.upgradeResourceBuilding(id, buildingType);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    // UPGRADE STORAGE BUILDING
    @PostMapping("/{id}/upgradeStorageBuilding/{buildingType}")
    public ResponseEntity<Planet> upgradeStorageBuilding(@PathVariable Long id, @PathVariable String buildingType) {
        try {
            service.upgradeStorageBuilding(id, buildingType);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    // UPGRADE MISC BUILDING
    @PostMapping("/{id}/upgradeMiscBuilding/{buildingType}")
    public ResponseEntity<Planet> upgradeMiscBuilding(@PathVariable Long id, @PathVariable String buildingType) {
        try {
            service.upgradeMiscBuilding(id, buildingType);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

}