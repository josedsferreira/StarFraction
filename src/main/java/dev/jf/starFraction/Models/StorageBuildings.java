package dev.jf.starFraction.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "storage_buildings")
public class StorageBuildings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="metal_storage_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer metalStorageLevel = 0;

    @Column(name="crystal_storage_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer crystalStorageLevel = 0;

    @Column(name="deuterium_storage_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer deuteriumStorageLevel = 0;

    // setters must only allow values to be incremented by 1
    public void setMetalStorageLevel() {
        this.metalStorageLevel = this.metalStorageLevel++;
    }

    public void setCrystalStorageLevel() {
        this.crystalStorageLevel = this.crystalStorageLevel++;
    }

    public void setDeuteriumStorageLevel() {
        this.deuteriumStorageLevel = this.deuteriumStorageLevel++;
    }
}
