package dev.jf.starFraction.planets;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

}
