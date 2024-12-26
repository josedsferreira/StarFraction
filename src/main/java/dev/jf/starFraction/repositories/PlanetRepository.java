package dev.jf.starFraction.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.jf.starFraction.Models.Planet;
import dev.jf.starFraction.Models.enums.PlanetSize;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long>{

    List<Planet> findByPlanetSize(PlanetSize planetSize);
    List<Planet> findByPlanetName(String planetName);
}
