package dev.jf.starFraction.Models;

import java.util.Random;

import dev.jf.starFraction.Models.enums.PlanetSize;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "planet")
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planet_id")
    private Long planetId;

    @Column(name = "planet_name", nullable = false, length = 255)
    private String planetName;

    @Enumerated(EnumType.STRING)
    @Column(name="planet_size")
    private PlanetSize planetSize;

    @Column(name="user_id")
    private Long userId;

    // Resource Buildings
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_buildings_id", referencedColumnName = "id")
    private ResourceBuildings resourceBuildings;

    // Storage
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "storage_buildings_id", referencedColumnName = "id")
    private StorageBuildings storageBuildings;

    // Facility Buildings
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "misc_buildings_id", referencedColumnName = "id")
    private MiscBuildings miscBuildings;

    public Planet() {
        this.planetSize = getRandomPlanetSize();
        this.resourceBuildings = new ResourceBuildings();
        this.storageBuildings = new StorageBuildings();
        this.miscBuildings = new MiscBuildings();
    }

    static public PlanetSize getRandomPlanetSize() {
        PlanetSize[] sizes = PlanetSize.values();
        int randomIndex = new Random().nextInt(sizes.length);
        return sizes[randomIndex];
    }

}
