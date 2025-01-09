package dev.jf.starFraction.game;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GameConfig {

    // Contruction Time
    public static Integer calculateBuildingTimeInSeconds(int metalCost, int CrystalCost) {
        int robotFactoryLevel = 1;
        double costs = (double)(metalCost + CrystalCost);
        double factor = 2500 * robotFactoryLevel;
        double result = (costs / factor) * 60;
        int integerResult = (int) result;

        /* System.out.println("");
        System.out.println("Construction time (double): " + result);
        System.out.println("Construction time (int): " + integerResult);
        System.out.println("Metal cost: " + metalCost);
        System.out.println("Crystal cost: " + CrystalCost);
        System.out.println("Costs: " + costs);
        System.out.println("Factor: " + factor); */

        if (result > 0) {
            return integerResult;
        } else {
            return 1;
        }
    }




    // METAL Mine production, cost, energy consumption
    public static final Integer METAL_MINE_UPGRADE_BASE_METAL_COST = 60;
    public static final Integer METAL_MINE_UPGRADE_BASE_CRYSTAL_COST = 15;

    public static Integer calculateMetalMineUpgradeMetalCost(int level) {
        return (int) (METAL_MINE_UPGRADE_BASE_METAL_COST * Math.pow(1.5, level - 1));
    }

    public static Integer calculateMetalMineUpgradeCrystalCost(int level) {
        return (int) (METAL_MINE_UPGRADE_BASE_CRYSTAL_COST * Math.pow(1.5, level - 1));
    }

    public static double calculateMetalMineProductionPerSecond(int level) {
        double production = (40 * level * Math.pow(1.1, level) + 40) / 60;
        BigDecimal bd = new BigDecimal(production).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Integer calculateMetalMineEnergyConsumption(int level) {
        return (int) (10 * level * Math.pow(1.1, level));
    }



    // CRYSTAL Mine production, cost, energy consumption
    public static final Integer CRYSTAL_MINE_UPGRADE_BASE_METAL_COST = 48;
    public static final Integer CRYSTAL_MINE_UPGRADE_BASE_CRYSTAL_COST = 24;

    public static Integer calculateCrystalMineUpgradeMetalCost(int level) {
        return (int) (CRYSTAL_MINE_UPGRADE_BASE_METAL_COST * Math.pow(1.6, level - 1));
    }

    public static Integer calculateCrystalMineUpgradeCrystalCost(int level) {
        return (int) (CRYSTAL_MINE_UPGRADE_BASE_CRYSTAL_COST * Math.pow(1.6, level - 1));
    }

    public static double calculateCrystalMineProductionPerSecond(int level) {
        double production = ((30 * level * Math.pow(1.1, level) + 30) / 60);
        BigDecimal bd = new BigDecimal(production).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Integer calculateCrystalMineEnergyConsumption(int level) {
        return (int) (10 * level * Math.pow(1.1, level));
    }



    // DEUTERIUM Mine production, cost, energy consumption
    public static final Integer DEUTERIUM_MINE_UPGRADE_BASE_METAL_COST = 35;
    public static final Integer DEUTERIUM_MINE_UPGRADE_BASE_CRYSTAL_COST = 30;

    public static Integer calculateDeuteriumMineUpgradeMetalCost(int level) {
        return (int) (DEUTERIUM_MINE_UPGRADE_BASE_METAL_COST * Math.pow(1.5, level - 1));
    }

    public static Integer calculateDeuteriumMineUpgradeCrystalCost(int level) {
        return (int) (DEUTERIUM_MINE_UPGRADE_BASE_CRYSTAL_COST * Math.pow(1.5, level - 1));
    }

    // the 20 here is a place holder for planet average temperature
    public static double calculateDeuteriumMineProductionPerSecond(int level) {
        double production = ((1 * level + 1));
        BigDecimal bd = new BigDecimal(production).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
        // at level 1, 1 deuterium per sec
    }

    public static Integer calculateDeuteriumMineEnergyConsumption(int level) {
        return (int) (20 * level * Math.pow(1.1, level));
    }


    // STORAGE
    // Metal Storage upgrade cost and capacity
    public static Integer calculateMetalStorageUpgradeMetalCost(int level) {
        return (int) (1000 * Math.pow(2, level - 1));
    }

    public static Integer calculateMetalStorageCapacity(int level) {
        return (int) (5000 * 2.5 * Math.pow(Math.E, (20/33) * level));
    }

    // Crystal Storage upgrade cost and capacity
    public static Integer calculateCrystalStorageUpgradeMetalCost(int level) {
        return (int) (500 * Math.pow(2, level + 1));
    }

    public static Integer calculateCrystalStorageUpgradeCrystalCost(int level) {
        return (int) (250 * Math.pow(2, level + 1));
    }

    public static Integer calculateCrystalStorageCapacity(int level) {
        return (int) (5000 * 2.5 * Math.pow(Math.E, (20/33) * level));
    }

    // Crystal Storage upgrade cost and capacity
    public static Integer calculateDeuteriumStorageUpgradeMetalCost(int level) {
        return (int) (1000 * Math.pow(2, level - 1));
    }

    public static Integer calculateDeuteriumStorageUpgradeCrystalCost(int level) {
        return (int) (1000 * Math.pow(2, level - 1));
    }

    public static Integer calculateDeuteriumStorageCapacity(int level) {
        return (int) (5000 * 2.5 * Math.pow(Math.E, (20/33) * level));
    }

}
