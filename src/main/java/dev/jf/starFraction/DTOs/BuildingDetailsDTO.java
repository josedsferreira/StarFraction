package dev.jf.starFraction.DTOs;

import dev.jf.starFraction.models.Planet;

public record BuildingDetailsDTO(
                    Planet planet,
                    int metalMineMetalUpgradeCost,
                    int metalMineCrystalUpgradeCost,
                    double metalProductionUpgrade,
                    int metalMineEnergyConsumption,
                    int metalMineUpgradeTime,
                    int crystalMineMetalUpgradeCost,
                    int crystalMineCrystalUpgradeCost,
                    double crystalProductionUpgrade,
                    int crystalMineEnergyConsumption,
                    int crystalMineUpgradeTime,
                    int deuteriumMineMetalUpgradeCost,
                    int deuteriumMineCrystalUpgradeCost,
                    double deuteriumProductionUpgrade,
                    int deuteriumMineEnergyConsumption,
                    int deuteriumMineUpgradeTime,
                    int solarPlantMetalUpgradeCost,
                    int solarPlantCrystalUpgradeCost,
                    int solarPlantUpgradeTime,
                    int shipyardMetalUpgradeCost,
                    int shipyardCrystalUpgradeCost,
                    int shipyardUpgradeTime,
                    int labMetalUpgradeCost,
                    int labCrystalUpgradeCost,
                    int labUpgradeTime) {
}