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
        if (canUpgradeBuilding(id, buildingType)) {
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
        else {
            throw new Exception("Not enough resources to upgrade building");
        }
    }

    public boolean canUpgradeBuilding(Long id, String buildingType) {
        updateResources(id);
        Planet planet = repository.findById(id).get();
        PlanetBuildings buildings = planet.getPlanetBuildings();
        switch (buildingType) {
            case "metalMine" -> {
                int metalCost = GameConfig.calculateMetalMineUpgradeMetalCost(buildings.getMetalMineLevel());
                int crystalCost = GameConfig.calculateMetalMineUpgradeCrystalCost(buildings.getMetalMineLevel());
                return buildings.getMetalAmount() >= metalCost && buildings.getCrystalAmount() >= crystalCost;
            }
            case "crystalMine" -> {
                int metalCost = GameConfig.calculateCrystalMineUpgradeMetalCost(buildings.getCrystalMineLevel());
                int crystalCost = GameConfig.calculateCrystalMineUpgradeCrystalCost(buildings.getCrystalMineLevel());
                return buildings.getMetalAmount() >= metalCost && buildings.getCrystalAmount() >= crystalCost;
            }
            case "deuteriumMine" -> {
                int metalCost = GameConfig.calculateDeuteriumMineUpgradeMetalCost(buildings.getDeuteriumMineLevel());
                int crystalCost = GameConfig.calculateDeuteriumMineUpgradeCrystalCost(buildings.getDeuteriumMineLevel());
                return buildings.getMetalAmount() >= metalCost && buildings.getCrystalAmount() >= crystalCost;
            }
            case "solarPlant" -> {
                return true;
            }
            case "metalStorage" -> {
                int metalCost = GameConfig.calculateMetalStorageUpgradeMetalCost(buildings.getMetalStorageLevel());
                return buildings.getMetalAmount() >= metalCost;
            }
            case "crystalStorage" -> {
                int metalCost = GameConfig.calculateCrystalStorageUpgradeMetalCost(buildings.getCrystalStorageLevel());
                int crystalCost = GameConfig.calculateCrystalStorageUpgradeCrystalCost(buildings.getCrystalStorageLevel());
                return buildings.getMetalAmount() >= metalCost && buildings.getCrystalAmount() >= crystalCost;
            }
            case "deuteriumStorage" -> {
                int metalCost = GameConfig.calculateDeuteriumStorageUpgradeMetalCost(buildings.getDeuteriumStorageLevel());
                int crystalCost = GameConfig.calculateDeuteriumStorageUpgradeCrystalCost(buildings.getDeuteriumStorageLevel());
                return buildings.getMetalAmount() >= metalCost && buildings.getCrystalAmount() >= crystalCost;
            }
            case "shipyard" -> {
                return true;
            }
            case "researchLab" -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public void updateResources(Long planetId) {
        Planet planet = repository.findById(planetId).get();
        PlanetBuildings buildings = planet.getPlanetBuildings();

        // Time elapsed
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(buildings.getLastUpdated(), now);
        long secondsElapsed = duration.getSeconds();

        // Constants
        double metalPerSecond = GameConfig.calculateMetalMineProductionPerSecond(buildings.getMetalMineLevel());
        double crystalPerSecond = GameConfig.calculateCrystalMineProductionPerSecond(buildings.getCrystalMineLevel());
        double deuteriumPerSecond = GameConfig.calculateDeuteriumMineProductionPerSecond(buildings.getDeuteriumMineLevel());

        // Resorces produced since then
        double metalProduced = metalPerSecond * (int) secondsElapsed;
        double crystalProduced = crystalPerSecond * (int) secondsElapsed;
        double deuteriumProduced = deuteriumPerSecond * (int) secondsElapsed;

        int newMetalAmount = (int) (buildings.getMetalAmount() + metalProduced);
        int newCrystalAmount = (int) (buildings.getCrystalAmount() + crystalProduced);
        int newDeuteriumAmount = (int) (buildings.getDeuteriumAmount() + deuteriumProduced);

        // Check if resources are over the storage limit
        if (newMetalAmount > GameConfig.calculateMetalStorageCapacity(buildings.getMetalStorageLevel())) {
            newMetalAmount = GameConfig.calculateMetalStorageCapacity(buildings.getMetalStorageLevel());
        }
        if (newCrystalAmount > GameConfig.calculateCrystalStorageCapacity(buildings.getCrystalStorageLevel())) {
            newCrystalAmount = GameConfig.calculateCrystalStorageCapacity(buildings.getCrystalStorageLevel());
        }
        if (newDeuteriumAmount > GameConfig.calculateDeuteriumStorageCapacity(buildings.getDeuteriumStorageLevel())) {
            newDeuteriumAmount = GameConfig.calculateDeuteriumStorageCapacity(buildings.getDeuteriumStorageLevel());
        }

        // Update resources
        buildings.setMetalAmount(newMetalAmount);
        buildings.setCrystalAmount(newCrystalAmount);
        buildings.setDeuteriumAmount(newDeuteriumAmount);

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
