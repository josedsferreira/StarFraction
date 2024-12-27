package dev.jf.starFraction.game;

public class GameConfig {

    // Metal Mine production, cost, energy consumption
    public static final Integer METAL_MINE_UPGRADE_BASE_METAL_COST = 60;
    public static final Integer METAL_MINE_UPGRADE_BASE_CRYSTAL_COST = 15;

    public static Integer calculateMetalMineUpgradeMetalCost(int level) {
        return (int) (METAL_MINE_UPGRADE_BASE_METAL_COST * Math.pow(1.5, level - 1));
    }

    public static Integer calculateMetalMineUpgradeCrystalCost(int level) {
        return (int) (METAL_MINE_UPGRADE_BASE_CRYSTAL_COST * Math.pow(1.5, level - 1));
    }

    public static Integer calculateMetalMineProductionPerSecond(int level) {
        return (int) ((30 * level * Math.pow(1.1, level) + 30) / 60);
    }

    public static Integer calculateMetalMineEnergyConsumption(int level) {
        return (int) (10 * level * Math.pow(1.1, level));
    }



    // Crystal Mine production, cost, energy consumption
    public static final Integer CRYSTAL_MINE_UPGRADE_BASE_METAL_COST = 48;
    public static final Integer CRYSTAL_MINE_UPGRADE_BASE_CRYSTAL_COST = 24;

    public static Integer calculateCrystalMineUpgradeMetalCost(int level) {
        return (int) (CRYSTAL_MINE_UPGRADE_BASE_METAL_COST * Math.pow(1.6, level - 1));
    }

    public static Integer calculateCrystalMineUpgradeCrystalCost(int level) {
        return (int) (CRYSTAL_MINE_UPGRADE_BASE_CRYSTAL_COST * Math.pow(1.6, level - 1));
    }

    public static Integer calculateCrystalMineProductionPerSecond(int level) {
        return (int) ((20 * level * Math.pow(1.1, level) + 20) / 60);
    }

    public static Integer calculateCrystalMineEnergyConsumption(int level) {
        return (int) (10 * level * Math.pow(1.1, level));
    }



    // Deuterium Mine production, cost, energy consumption
    public static final Integer DEUTERIUM_MINE_UPGRADE_BASE_METAL_COST = 35;
    public static final Integer DEUTERIUM_MINE_UPGRADE_BASE_CRYSTAL_COST = 30;

    public static Integer calculateDeuteriumMineUpgradeMetalCost(int level) {
        return (int) (DEUTERIUM_MINE_UPGRADE_BASE_METAL_COST * Math.pow(1.5, level - 1));
    }

    public static Integer calculateDeuteriumMineUpgradeCrystalCost(int level) {
        return (int) (DEUTERIUM_MINE_UPGRADE_BASE_CRYSTAL_COST * Math.pow(1.5, level - 1));
    }

    // the 20 here is a place holder for planet average temperature
    public static Integer calculateDeuteriumMineProductionPerSecond(int level) {
        return (int) ((10 * level * 20 + 10) / 60);
    }

    public static Integer calculateDeuteriumMineEnergyConsumption(int level) {
        return (int) (20 * level * Math.pow(1.1, level));
    }


}
