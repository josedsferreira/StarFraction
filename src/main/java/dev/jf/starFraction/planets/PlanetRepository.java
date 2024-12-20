package dev.jf.starFraction.planets;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long>{

    List<Planet> findByPlanetSize(PlanetSize planetSize);
    List<Planet> findByPlanetName(String planetName);
}
