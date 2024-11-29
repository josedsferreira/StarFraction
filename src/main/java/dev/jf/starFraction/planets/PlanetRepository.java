package dev.jf.starFraction.planets;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long>{

    Optional<Planet> findByPlanetSize(PlanetSize planetSize);
    Optional<Planet> findByPlanetName(String planetName);
}
