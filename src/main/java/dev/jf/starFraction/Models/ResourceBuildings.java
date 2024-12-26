package dev.jf.starFraction.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "resource_buildings")
public class ResourceBuildings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="metal_mine_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer metalMineLevel = 0;

    @Column(name="crystal_mine_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer crystalMineLevel = 0;

    @Column(name="deuterium_mine_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer deuteriumMineLevel = 0;

    @Column(name="solar_plant_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer solarPlantLevel = 0;

    // setters must only allow values to be incremented by 1
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
