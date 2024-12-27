package dev.jf.starFraction.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.jf.starFraction.Models.Planet;
import dev.jf.starFraction.Models.PlanetBuildings;
import dev.jf.starFraction.Models.enums.PlanetSize;
import dev.jf.starFraction.repositories.PlanetRepository;

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

    public List<Planet> getPlanetByName(String name) {
        return repository.findByPlanetName(name);
    }

    public void updatePlanetName(Long id, String name) {
        Planet planet = repository.findById(id).get();
        planet.setPlanetName(name);
        repository.save(planet);
    }

    public List<Planet> getPlanetsBySize(PlanetSize size) {
        return repository.findByPlanetSize(size);
    }

    public PlanetBuildings getPlanetBuildings(Long id) {
        return repository.findById(id).get().getPlanetBuildings();
    }

    public void updatePlanetBuildings(Long id, PlanetBuildings planetBuildings) {
        Planet planet = repository.findById(id).get();
        planet.setPlanetBuildings(planetBuildings);
        repository.save(planet);
    }

    public void upgradePlanetBuilding(Long id, String buildingType) throws Exception {
        Planet planet = repository.findById(id).get();
        switch (buildingType) {
            case "metalMine" -> planet.getPlanetBuildings().setMetalMineLevel();
            case "crystalMine" -> planet.getPlanetBuildings().setCrystalMineLevel();
            case "deuteriumMine" -> planet.getPlanetBuildings().setDeuteriumMineLevel();
            case "solarPlant" -> planet.getPlanetBuildings().setSolarPlantLevel();
            case "metalStorage" -> planet.getPlanetBuildings().setMetalStorageLevel();
            case "crystalStorage" -> planet.getPlanetBuildings().setCrystalStorageLevel();
            case "deuteriumStorage" -> planet.getPlanetBuildings().setDeuteriumStorageLevel();
            case "shipyard" -> planet.getPlanetBuildings().setShipyardLevel();
            case "researchLab" -> planet.getPlanetBuildings().setResearchLabLevel();
            default -> {
                throw new Exception("Invalid building type");
            }
        }
        repository.save(planet);
    }


}
