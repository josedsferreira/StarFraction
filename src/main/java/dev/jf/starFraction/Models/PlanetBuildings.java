package dev.jf.starFraction.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "planet_buildings")
public class PlanetBuildings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Production Buildings

    @Column(name="metal_mine_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer metalMineLevel = 0;

    @Column(name="crystal_mine_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer crystalMineLevel = 0;

    @Column(name="deuterium_mine_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer deuteriumMineLevel = 0;

    @Column(name="solar_plant_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer solarPlantLevel = 0;


    // Storage Buildings

    @Column(name="metal_storage_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer metalStorageLevel = 0;

    @Column(name="crystal_storage_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer crystalStorageLevel = 0;

    @Column(name="deuterium_storage_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer deuteriumStorageLevel = 0;

    @Column(name="shipyard_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer shipyardLevel = 0;

    @Column(name="research_lab_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer researchLabLevel = 0;


    // Resource amounts

    @Column(name="metal_amount", columnDefinition = "INTEGER DEFAULT 0")
    private Integer metalAmount = 0;

    @Column(name="crystal_amount", columnDefinition = "INTEGER DEFAULT 0")
    private Integer crystalAmount = 0;

    @Column(name="deuterium_amount", columnDefinition = "INTEGER DEFAULT 0")
    private Integer deuteriumAmount = 0;

    @Column(name="energy_amount", columnDefinition = "INTEGER DEFAULT 0")
    private Integer energyAmount = 0;

    // Timestamp
    @Column(name = "last_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdated = LocalDateTime.now();



    // setters for building levels must only allow values to be incremented by 1
    public void setShipyardLevel() {
        this.shipyardLevel = this.shipyardLevel++;
    }

    public void setResearchLabLevel() {
        this.researchLabLevel = this.researchLabLevel++;
    }

    public void setMetalStorageLevel() {
        this.metalStorageLevel = this.metalStorageLevel++;
    }

    public void setCrystalStorageLevel() {
        this.crystalStorageLevel = this.crystalStorageLevel++;
    }

    public void setDeuteriumStorageLevel() {
        this.deuteriumStorageLevel = this.deuteriumStorageLevel++;
    }

    public void setMetalMineLevel() {
        this.metalMineLevel = this.metalMineLevel++;
    }

    public void setCrystalMineLevel() {
        this.crystalMineLevel = this.crystalMineLevel++;
    }

    public void setDeuteriumMineLevel() {
        this.deuteriumMineLevel = this.deuteriumMineLevel++;
    }

    public void setSolarPlantLevel() {
        this.solarPlantLevel = this.solarPlantLevel++;
    }
}
