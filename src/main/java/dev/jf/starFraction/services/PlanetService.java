package dev.jf.starFraction.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.jf.starFraction.Models.MiscBuildings;
import dev.jf.starFraction.Models.Planet;
import dev.jf.starFraction.Models.ResourceBuildings;
import dev.jf.starFraction.Models.StorageBuildings;
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

    public ResourceBuildings getResourceBuildings(Long id) {
        return repository.findById(id).get().getResourceBuildings();
    }

    public void updateResourceBuildings(Long id, ResourceBuildings resourceBuildings) {
        Planet planet = repository.findById(id).get();
        planet.setResourceBuildings(resourceBuildings);
        repository.save(planet);
    }

    public void upgradeResourceBuilding(Long id, String buildingType) throws Exception {
        Planet planet = repository.findById(id).get();
        switch (buildingType) {
            case "metalMine" -> planet.getResourceBuildings().setMetalMineLevel();
            case "crystalMine" -> planet.getResourceBuildings().setCrystalMineLevel();
            case "deuteriumMine" -> planet.getResourceBuildings().setDeuteriumMineLevel();
            case "solarPlant" -> planet.getResourceBuildings().setSolarPlantLevel();
            default -> {
                throw new Exception("Invalid building type");
            }
        }
        repository.save(planet);
    }

    public void upgradeStorageBuilding(Long id, String buildingType) throws Exception {
        Planet planet = repository.findById(id).get();
        switch (buildingType) {
            case "metalStorage" -> planet.getStorageBuildings().setMetalStorageLevel();
            case "crystalStorage" -> planet.getStorageBuildings().setCrystalStorageLevel();
            case "deuteriumStorage" -> planet.getStorageBuildings().setDeuteriumStorageLevel();
            default -> {
                throw new Exception("Invalid building type");
            }
        }
        repository.save(planet);
    }

    public void upgradeMiscBuilding(Long id, String buildingType) throws Exception {
        Planet planet = repository.findById(id).get();
        switch (buildingType) {
            case "shipyard" -> planet.getMiscBuildings().setShipyardLevel();
            case "researchLab" -> planet.getMiscBuildings().setResearchLabLevel();
            default -> {
                throw new Exception("Invalid building type");
            }
        }
        repository.save(planet);
    }

    public StorageBuildings getStorageBuildings(Long id) {
        return repository.findById(id).get().getStorageBuildings();
    }

    public void updateStorageBuildings(Long id, StorageBuildings storageBuildings) {
        Planet planet = repository.findById(id).get();
        planet.setStorageBuildings(storageBuildings);
        repository.save(planet);
    }

    public MiscBuildings getMiscBuildings(Long id) {
        return repository.findById(id).get().getMiscBuildings();
    }

    public void updateMiscBuildings(Long id, MiscBuildings miscBuildings) {
        Planet planet = repository.findById(id).get();
        planet.setMiscBuildings(miscBuildings);
        repository.save(planet);
    }


}
