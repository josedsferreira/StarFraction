package dev.jf.starFraction.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.jf.starFraction.game.GameConfig;
import dev.jf.starFraction.models.Planet;
import dev.jf.starFraction.models.PlanetBuildings;
import dev.jf.starFraction.models.enums.PlanetSize;
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
        PlanetBuildings buildings = planet.getPlanetBuildings();
        switch (buildingType) {
            case "metalMine" -> buildings.incrementMetalMineLevel();
            case "crystalMine" -> buildings.incrementCrystalMineLevel();
            case "deuteriumMine" -> buildings.incrementDeuteriumMineLevel();
            case "solarPlant" -> buildings.incrementSolarPlantLevel();
            case "metalStorage" -> buildings.incrementMetalStorageLevel();
            case "crystalStorage" -> buildings.incrementCrystalStorageLevel();
            case "deuteriumStorage" -> buildings.incrementDeuteriumStorageLevel();
            case "shipyard" -> buildings.incrementShipyardLevel();
            case "researchLab" -> buildings.incrementResearchLabLevel();
            default -> throw new Exception("Invalid building type");
        }
        planet.setPlanetBuildings(buildings);
        System.out.println("Upgraded " + buildingType);
        System.out.println("It is now level: " + planet.getPlanetBuildings().getMetalMineLevel());
        repository.save(planet);
    }

    public void updateResources(Long planetId) {
        Planet planet = repository.findById(planetId).get();
        PlanetBuildings buildings = planet.getPlanetBuildings();

        // Time elapsed
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(buildings.getLastUpdated(), now);
        long secondsElapsed = duration.getSeconds();

        // Constants
        int metalPerSecond = GameConfig.calculateMetalMineProductionPerSecond(buildings.getMetalMineLevel());
        int crystalPerSecond = GameConfig.calculateCrystalMineProductionPerSecond(buildings.getCrystalMineLevel());
        int deuteriumPerSecond = GameConfig.calculateDeuteriumMineProductionPerSecond(buildings.getDeuteriumMineLevel());
        // Resorces produced since then
        int metalProduced = buildings.getMetalMineLevel() * metalPerSecond * (int) secondsElapsed;
        int crystalProduced = buildings.getCrystalMineLevel() * crystalPerSecond * (int) secondsElapsed;
        int deuteriumProduced = buildings.getDeuteriumMineLevel() * deuteriumPerSecond * (int) secondsElapsed;

        // Update resources
        buildings.setMetalAmount(buildings.getMetalAmount() + metalProduced);
        buildings.setCrystalAmount(buildings.getCrystalAmount() + crystalProduced);
        buildings.setDeuteriumAmount(buildings.getDeuteriumAmount() + deuteriumProduced);

        // set last updated to now
        buildings.setLastUpdated(now);

        // save
        System.out.println("updated resources");
        System.out.println("Production rates are:");
        System.out.println("metal: " + metalPerSecond);
        System.out.println("crystal: " + crystalPerSecond);
        System.out.println("deuterium: " + deuteriumPerSecond);
        System.out.println("metal is now:" + buildings.getMetalAmount());
        System.out.println("crystal is now:" + buildings.getCrystalAmount());
        System.out.println("deuterium is now:" + buildings.getDeuteriumAmount());
        planet.setPlanetBuildings(buildings);
        repository.save(planet);
    }


}
