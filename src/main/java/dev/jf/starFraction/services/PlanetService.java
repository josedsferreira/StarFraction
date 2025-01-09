package dev.jf.starFraction.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.jf.starFraction.DTOs.BuildingDetailsDTO;
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

    public void orderPlanetBuildingUpgrade(Long id, String buildingType) throws Exception {
        if (canUpgradeBuilding(id, buildingType)) {
            // Pay costs
            int metalCost = getMetalUpgradeCosts(id, buildingType);
            int crystalCost = getCrystalUpgradeCosts(id, buildingType);
            payCosts(id, metalCost, crystalCost);
            // Set building being upgraded
            setBuildingBeingUpgraded(id, buildingType);
            System.out.println("Building upgrade ordered for " + buildingType);
        }
        else {
            throw new Exception("Building not possible to to missing resources or upgrade already in progress");
        }
    }

    public void finishPlanetBuildingUpgrade(Long id, String buildingType) throws Exception {
        if (canUpgradeBuilding(id, buildingType)) {
            Planet planet = repository.findById(id).get();
            PlanetBuildings buildings = planet.getPlanetBuildings();

            switch (buildingType) {
                case "Metal Mine" -> buildings.incrementMetalMineLevel();
                case "Crystal Mine" -> buildings.incrementCrystalMineLevel();
                case "Deuterium Rig" -> buildings.incrementDeuteriumMineLevel();
                case "Solar Plant" -> buildings.incrementSolarPlantLevel();
                case "metalStorage" -> buildings.incrementMetalStorageLevel();
                case "crystalStorage" -> buildings.incrementCrystalStorageLevel();
                case "deuteriumStorage" -> buildings.incrementDeuteriumStorageLevel();
                case "shipyard" -> buildings.incrementShipyardLevel();
                case "researchLab" -> buildings.incrementResearchLabLevel();
                default -> throw new Exception("Invalid building type");
            }
            planet.setPlanetBuildings(buildings);
            /* System.out.println("Upgraded " + buildingType); */
            System.out.println("It is now level: " + planet.getPlanetBuildings().getMetalMineLevel());
            repository.save(planet);
        }
        else {
            throw new Exception("Building not possible to to missing resources or upgrade already in progress");
        }
    }

    public boolean canUpgradeBuilding(Long id, String buildingType) {
        updateResources(id);
        Planet planet = repository.findById(id).get();
        PlanetBuildings buildings = planet.getPlanetBuildings();
        if (!"None".equals(buildings.getBuildingBeingUpgraded())) {
            System.out.println("Building is already being upgraded");
            return false;
        }
        switch (buildingType) {
            case "Metal Mine" -> {
                int metalCost = GameConfig.calculateMetalMineUpgradeMetalCost(buildings.getMetalMineLevel());
                int crystalCost = GameConfig.calculateMetalMineUpgradeCrystalCost(buildings.getMetalMineLevel());
                return buildings.getMetalAmount() >= metalCost && buildings.getCrystalAmount() >= crystalCost;
            }
            case "Crystal Mine" -> {
                int metalCost = GameConfig.calculateCrystalMineUpgradeMetalCost(buildings.getCrystalMineLevel());
                int crystalCost = GameConfig.calculateCrystalMineUpgradeCrystalCost(buildings.getCrystalMineLevel());
                return buildings.getMetalAmount() >= metalCost && buildings.getCrystalAmount() >= crystalCost;
            }
            case "Deuterium Rig" -> {
                int metalCost = GameConfig.calculateDeuteriumMineUpgradeMetalCost(buildings.getDeuteriumMineLevel());
                int crystalCost = GameConfig.calculateDeuteriumMineUpgradeCrystalCost(buildings.getDeuteriumMineLevel());
                return buildings.getMetalAmount() >= metalCost && buildings.getCrystalAmount() >= crystalCost;
            }
            case "Solar Plant" -> {
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
            case "Shipyard" -> {
                return true;
            }
            case "Lab" -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public int getMetalUpgradeCosts(Long id, String buildingType) {
        updateResources(id);
        Planet planet = repository.findById(id).get();
        PlanetBuildings buildings = planet.getPlanetBuildings();
        switch (buildingType) {
            case "Metal Mine" -> {
                return GameConfig.calculateMetalMineUpgradeMetalCost(buildings.getMetalMineLevel());
            }
            case "Crystal Mine" -> {
                return GameConfig.calculateCrystalMineUpgradeMetalCost(buildings.getCrystalMineLevel());
            }
            case "Deuterium Rig" -> {
                return GameConfig.calculateDeuteriumMineUpgradeMetalCost(buildings.getDeuteriumMineLevel());
            }
            case "Solar Plant" -> {
                return 0;
            }
            case "metalStorage" -> {
                return GameConfig.calculateMetalStorageUpgradeMetalCost(buildings.getMetalStorageLevel());
            }
            case "crystalStorage" -> {
                return GameConfig.calculateCrystalStorageUpgradeMetalCost(buildings.getCrystalStorageLevel());
            }
            case "deuteriumStorage" -> {
                return GameConfig.calculateDeuteriumStorageUpgradeMetalCost(buildings.getDeuteriumStorageLevel());
            }
            case "Shipyard" -> {
                return 0;
            }
            case "Lab" -> {
                return 0;
            }
            default -> {
                return 0;
            }
        }
    }

    public int getCrystalUpgradeCosts(Long id, String buildingType) {
        updateResources(id);
        Planet planet = repository.findById(id).get();
        PlanetBuildings buildings = planet.getPlanetBuildings();
        switch (buildingType) {
            case "Metal Mine" -> {
                return GameConfig.calculateMetalMineUpgradeCrystalCost(buildings.getMetalMineLevel());
            }
            case "Crystal Mine" -> {
                return GameConfig.calculateCrystalMineUpgradeCrystalCost(buildings.getCrystalMineLevel());
            }
            case "Deuterium Rig" -> {
                return GameConfig.calculateDeuteriumMineUpgradeCrystalCost(buildings.getDeuteriumMineLevel());
            }
            case "Solar Plant" -> {
                return 0;
            }
            case "metalStorage" -> {
                return 0;
            }
            case "crystalStorage" -> {
                return GameConfig.calculateCrystalStorageUpgradeCrystalCost(buildings.getCrystalStorageLevel());
            }
            case "deuteriumStorage" -> {
                return GameConfig.calculateDeuteriumStorageUpgradeCrystalCost(buildings.getDeuteriumStorageLevel());
            }
            case "Shipyard" -> {
                return 0;
            }
            case "Lab" -> {
                return 0;
            }
            default -> {
                return 0;
            }
        }
    }

    public void payCosts(Long id, int metalCost, int crystalCost) {
        Planet planet = repository.findById(id).get();
        PlanetBuildings buildings = planet.getPlanetBuildings();
        if (metalCost > buildings.getMetalAmount() || crystalCost > buildings.getCrystalAmount()) {
            throw new IllegalArgumentException("Not enough resources to pay costs");
        }
        else {
            buildings.setMetalAmount(buildings.getMetalAmount() - metalCost);
            buildings.setCrystalAmount(buildings.getCrystalAmount() - crystalCost);
            repository.save(planet);
            updateResources(id);
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
        /* System.out.println("updated resources");
        System.out.println("Production rates are:");
        System.out.println("metal: " + metalPerSecond);
        System.out.println("crystal: " + crystalPerSecond);
        System.out.println("deuterium: " + deuteriumPerSecond);
        System.out.println("metal is now:" + buildings.getMetalAmount());
        System.out.println("crystal is now:" + buildings.getCrystalAmount());
        System.out.println("deuterium is now:" + buildings.getDeuteriumAmount()); */
        planet.setPlanetBuildings(buildings);
        repository.save(planet);
    }

    public BuildingDetailsDTO getPlanetBuildingDetails(Long planetId) {
        Planet planet = repository.findById(planetId).get();
        PlanetBuildings buildings = planet.getPlanetBuildings();

        // METAL MINE
        int metalMineMetalUpgradecost = GameConfig.calculateMetalMineUpgradeMetalCost(buildings.getMetalMineLevel());
        int metalMineCrystalUpgradecost = GameConfig.calculateMetalMineUpgradeCrystalCost(buildings.getMetalMineLevel());

        double metalProductionUpgrade = GameConfig.calculateMetalMineProductionPerSecond(buildings.getMetalMineLevel() + 1) - GameConfig.calculateMetalMineProductionPerSecond(buildings.getMetalMineLevel());
        BigDecimal bd = new BigDecimal(metalProductionUpgrade).setScale(2, RoundingMode.HALF_UP);
        metalProductionUpgrade = bd.doubleValue();

        int metalMineEnergyConsumption = GameConfig.calculateMetalMineEnergyConsumption(buildings.getMetalMineLevel() + 1) - GameConfig.calculateMetalMineEnergyConsumption(buildings.getMetalMineLevel());
        int metalMineUpgradeTime = GameConfig.calculateBuildingTimeInSeconds(metalMineMetalUpgradecost, metalMineCrystalUpgradecost);
        /* System.out.println("metal mine upgrade time: " + metalMineUpgradeTime);
        System.out.println("metal mine metal cost: " + metalMineMetalUpgradecost);
        System.out.println("metal mine crystal cost: " + metalMineCrystalUpgradecost); */

        // CRYSTAL MINE
        int crystalMineMetalUpgradecost = GameConfig.calculateCrystalMineUpgradeMetalCost(buildings.getCrystalMineLevel());
        int crystalMineCrystalUpgradecost = GameConfig.calculateCrystalMineUpgradeCrystalCost(buildings.getCrystalMineLevel());
        double crystalProductionUpgrade = GameConfig.calculateCrystalMineProductionPerSecond(buildings.getCrystalMineLevel() + 1) - GameConfig.calculateCrystalMineProductionPerSecond(buildings.getCrystalMineLevel());
        bd = BigDecimal.valueOf(crystalProductionUpgrade).setScale(2, RoundingMode.HALF_UP);
        crystalProductionUpgrade = bd.doubleValue();
        int crystalMineEnergyConsumption = GameConfig.calculateCrystalMineEnergyConsumption(buildings.getCrystalMineLevel() + 1) - GameConfig.calculateCrystalMineEnergyConsumption(buildings.getCrystalMineLevel());
        int crystalMineUpgradeTime = GameConfig.calculateBuildingTimeInSeconds(crystalMineMetalUpgradecost, crystalMineCrystalUpgradecost);

        // DEUTERIUM MINE
        int deuteriumMineMetalUpgradecost = GameConfig.calculateDeuteriumMineUpgradeMetalCost(buildings.getDeuteriumMineLevel());
        int deuteriumMineCrystalUpgradecost = GameConfig.calculateDeuteriumMineUpgradeCrystalCost(buildings.getDeuteriumMineLevel());
        double deuteriumProductionUpgrade = GameConfig.calculateDeuteriumMineProductionPerSecond(buildings.getDeuteriumMineLevel() + 1) - GameConfig.calculateDeuteriumMineProductionPerSecond(buildings.getDeuteriumMineLevel());
        bd = BigDecimal.valueOf(deuteriumProductionUpgrade).setScale(2, RoundingMode.HALF_UP);
        deuteriumProductionUpgrade = bd.doubleValue();
        int deuteriumMineEnergyConsumption = GameConfig.calculateDeuteriumMineEnergyConsumption(buildings.getDeuteriumMineLevel() + 1) - GameConfig.calculateDeuteriumMineEnergyConsumption(buildings.getDeuteriumMineLevel());
        int deuteriumMineUpgradeTime = GameConfig.calculateBuildingTimeInSeconds(deuteriumMineMetalUpgradecost, deuteriumMineCrystalUpgradecost);

        // SOLAR PLANT
        // we'll use the metal mine costs as a placeholder for the solar plant
        int solarPlantMetalUpgradecost = GameConfig.calculateMetalMineUpgradeMetalCost(buildings.getSolarPlantLevel());
        int solarPlantCrystalUpgradecost = GameConfig.calculateMetalMineUpgradeCrystalCost(buildings.getSolarPlantLevel());
        int solarPlantUpgradeTime = GameConfig.calculateBuildingTimeInSeconds(solarPlantMetalUpgradecost, solarPlantCrystalUpgradecost);

        // SHIPYARD
        // we'll use the metal mine costs as a placeholder for the shipyard
        int shipyardMetalUpgradecost = GameConfig.calculateMetalMineUpgradeMetalCost(buildings.getShipyardLevel());
        int shipyardCrystalUpgradecost = GameConfig.calculateMetalMineUpgradeCrystalCost(buildings.getShipyardLevel());
        int shipyardUpgradeTime = GameConfig.calculateBuildingTimeInSeconds(shipyardMetalUpgradecost, shipyardCrystalUpgradecost);

        // RESEARCH LAB
        // we'll use the metal mine costs as a placeholder for the research lab
        int labMetalUpgradeCost = GameConfig.calculateMetalMineUpgradeMetalCost(buildings.getResearchLabLevel());
        int labCrystalUpgradeCost = GameConfig.calculateMetalMineUpgradeCrystalCost(buildings.getResearchLabLevel());
        int labUpgradeTime = GameConfig.calculateBuildingTimeInSeconds(labMetalUpgradeCost, labCrystalUpgradeCost);

        BuildingDetailsDTO buildingDetailsDTO = new BuildingDetailsDTO(
            planet,
            metalMineMetalUpgradecost,
            metalMineCrystalUpgradecost,
            metalProductionUpgrade,
            metalMineEnergyConsumption,
            metalMineUpgradeTime,
            crystalMineMetalUpgradecost,
            crystalMineCrystalUpgradecost,
            crystalProductionUpgrade,
            crystalMineEnergyConsumption,
            crystalMineUpgradeTime,
            deuteriumMineMetalUpgradecost,
            deuteriumMineCrystalUpgradecost,
            deuteriumProductionUpgrade,
            deuteriumMineEnergyConsumption,
            deuteriumMineUpgradeTime,
            solarPlantMetalUpgradecost,
            solarPlantCrystalUpgradecost,
            solarPlantUpgradeTime,
            shipyardMetalUpgradecost,
            shipyardCrystalUpgradecost,
            shipyardUpgradeTime,
            labMetalUpgradeCost,
            labCrystalUpgradeCost,
            labUpgradeTime
        );

        return buildingDetailsDTO;
    
    }

    public void setBuildingBeingUpgraded(Long planetId, String buildingType) {
        Planet planet = repository.findById(planetId).get();
        PlanetBuildings buildings = planet.getPlanetBuildings();
        if ((buildings.getBuildingBeingUpgraded()) != null && (!"None".equals(buildings.getBuildingBeingUpgraded()))) {
            throw new IllegalArgumentException("A building is already being upgraded");
        }
        else {
            buildings.setBuildingBeingUpgraded(buildingType);
            LocalDateTime upgradeFinishTime = calculateUpgradeFinishTime(planet, buildingType);
            buildings.setUpgradeFinishTime(upgradeFinishTime);
            planet.setPlanetBuildings(buildings);
            repository.save(planet);
        }
    }

    public LocalDateTime calculateUpgradeFinishTime(Planet planet, String buildingType) {
        PlanetBuildings buildings = planet.getPlanetBuildings();
        int upgradeTime = 0;
        switch (buildingType) {
            case "Metal Mine" -> upgradeTime = GameConfig.calculateBuildingTimeInSeconds(GameConfig.calculateMetalMineUpgradeMetalCost(buildings.getMetalMineLevel()), GameConfig.calculateMetalMineUpgradeCrystalCost(buildings.getMetalMineLevel()));
            case "Crystal Mine" -> upgradeTime = GameConfig.calculateBuildingTimeInSeconds(GameConfig.calculateCrystalMineUpgradeMetalCost(buildings.getCrystalMineLevel()), GameConfig.calculateCrystalMineUpgradeCrystalCost(buildings.getCrystalMineLevel()));
            case "Deuterium Rig" -> upgradeTime = GameConfig.calculateBuildingTimeInSeconds(GameConfig.calculateDeuteriumMineUpgradeMetalCost(buildings.getDeuteriumMineLevel()), GameConfig.calculateDeuteriumMineUpgradeCrystalCost(buildings.getDeuteriumMineLevel()));
            case "Solar Plant" -> upgradeTime = GameConfig.calculateBuildingTimeInSeconds(GameConfig.calculateMetalMineUpgradeMetalCost(buildings.getSolarPlantLevel()), GameConfig.calculateMetalMineUpgradeCrystalCost(buildings.getSolarPlantLevel()));
            case "metalStorage" -> upgradeTime = GameConfig.calculateBuildingTimeInSeconds(GameConfig.calculateMetalStorageUpgradeMetalCost(buildings.getMetalStorageLevel()), 0);
            case "crystalStorage" -> upgradeTime = GameConfig.calculateBuildingTimeInSeconds(GameConfig.calculateCrystalStorageUpgradeMetalCost(buildings.getCrystalStorageLevel()), GameConfig.calculateCrystalStorageUpgradeCrystalCost(buildings.getCrystalStorageLevel()));
            case "deuteriumStorage" -> upgradeTime = GameConfig.calculateBuildingTimeInSeconds(GameConfig.calculateDeuteriumStorageUpgradeMetalCost(buildings.getDeuteriumStorageLevel()), GameConfig.calculateDeuteriumStorageUpgradeCrystalCost(buildings.getDeuteriumStorageLevel()));
            case "shipyard" -> upgradeTime = GameConfig.calculateBuildingTimeInSeconds(GameConfig.calculateMetalMineUpgradeMetalCost(buildings.getShipyardLevel()), GameConfig.calculateMetalMineUpgradeCrystalCost(buildings.getShipyardLevel()));
            case "researchLab" -> upgradeTime = GameConfig.calculateBuildingTimeInSeconds(GameConfig.calculateMetalMineUpgradeMetalCost(buildings.getResearchLabLevel()), GameConfig.calculateMetalMineUpgradeCrystalCost(buildings.getResearchLabLevel()));
        }
        LocalDateTime upgradeFinishTime = LocalDateTime.now().plusSeconds(upgradeTime);
        return upgradeFinishTime;
    }

    public boolean checkIfUpgradeFinished(Long planetId) {
        Planet planet = repository.findById(planetId).get();
        PlanetBuildings buildings = planet.getPlanetBuildings();
        String buildingBeingUpgraded = buildings.getBuildingBeingUpgraded();
        if (buildingBeingUpgraded == null || "None".equals(buildingBeingUpgraded)) {
            return true;
        }
        else {
            LocalDateTime upgradeFinishTime = buildings.getUpgradeFinishTime();
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(upgradeFinishTime)) {
                try {
                    finishPlanetBuildingUpgrade(planetId, buildingBeingUpgraded);
                } catch (Exception e) {
                    System.out.println("Upgrade failed: " + e.getMessage());
                }
                buildings.setBuildingBeingUpgraded("None");
                planet.setPlanetBuildings(buildings);
                repository.save(planet);
                return true;
            }
            else {
                return false;
            }
        }
    }

}
