package dev.jf.starFraction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.jf.starFraction.models.Planet;
import dev.jf.starFraction.models.enums.PlanetSize;
import dev.jf.starFraction.repositories.PlanetRepository;
import dev.jf.starFraction.services.PlanetService;

public class PlanetServiceTest {

    @Mock
    private PlanetRepository planetRepositoryMock;

    @InjectMocks
    private PlanetService planetServiceMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_planet_exists_when_get_all_planets_return_all_planets() {
        // Given
        Planet planet = new Planet();
        planet.setPlanetName("Earth");
        planet.setPlanetSize(PlanetSize.medium);

        // technically still part of Given even though its called when
        when(planetRepositoryMock.findAll()).thenReturn(List.of(planet));

        // When
        List<Planet> planets = planetServiceMock.getAllPlanets();

        // Then
        assertNotNull(planets, "The planets list should not be null");
        assertEquals(1, planets.size(), "The planets list should contain exactly one planet");
        assertEquals("Earth", planets.get(0).getPlanetName(), "The name of the planet should be Earth");
        assertEquals(PlanetSize.medium, planets.get(0).getPlanetSize(), "The size of the planet should be medium");
    }

    @Test
    public void given_planet_exists_when_get_planet_by_name_return_planet() {
        // Given
        Planet planet = new Planet();
        planet.setPlanetName("Earth");
        planet.setPlanetSize(PlanetSize.medium);

        // technically still part of Given even though its called when
        when(planetRepositoryMock.findByPlanetName("Earth")).thenReturn(java.util.List.of(planet));

        // When
        List<Planet> planetList = planetServiceMock.getPlanetByName("Earth");

        // Then
        if (planetList.isEmpty()) {
            fail("The planet was not found");
        }
        else {
            assertNotNull(planetList, "The planet should not be null");
            assertEquals(1, planetList.size(), "The planets list should contain exactly one planet");
            assertEquals("Earth", planetList.get(0).getPlanetName(), "The name of the planet should be Earth");
            assertEquals(PlanetSize.medium, planetList.get(0).getPlanetSize(), "The size of the planet should be medium");
        }
    }

    @Test
    public void given_planet_exists_when_get_planet_by_size_return_planet() {
        // Given
        Planet planet = new Planet();
        planet.setPlanetName("Earth");
        planet.setPlanetSize(PlanetSize.medium);

        // technically still part of Given even though its called when
        when(planetRepositoryMock.findByPlanetSize(PlanetSize.medium)).thenReturn(java.util.List.of(planet));

        // When
        List<Planet> planetList = planetServiceMock.getPlanetsBySize(PlanetSize.medium);

        // Then
        if (planetList.isEmpty()) {
            fail("The planet was not found");
        }
        else {
            assertNotNull(planetList, "The planet should not be null");
            assertEquals(1, planetList.size(), "The planets list should contain exactly one planet");
            assertEquals("Earth", planetList.get(0).getPlanetName(), "The name of the planet should be Earth");
            assertEquals(PlanetSize.medium, planetList.get(0).getPlanetSize(), "The size of the planet should be medium");
        }
    }

    @Test
    public void given_planet_exists_when_get_planet_by_id_return_planet() {
        // Given
        Planet planet = new Planet();
        planet.setPlanetName("Earth");
        planet.setPlanetSize(PlanetSize.medium);

        // technically still part of Given even though its called when
        when(planetRepositoryMock.findById(1L)).thenReturn(Optional.of(planet));

        // When
        Optional<Planet> planetOptional = planetServiceMock.getPlanetById(1L);

        // Then
        if (planetOptional.isEmpty()) {
            fail("The planet was not found");
        }
        else {
            assertNotNull(planetOptional, "The planet should not be null");
            assertEquals("Earth", planetOptional.get().getPlanetName(), "The name of the planet should be Earth");
            assertEquals(PlanetSize.medium, planetOptional.get().getPlanetSize(), "The size of the planet should be medium");
        }
    }

    @Test
    public void given_planet_exists_when_save_planet_return_planet() {
        // Given
        Planet planet = new Planet();
        planet.setPlanetName("Earth");
        planet.setPlanetSize(PlanetSize.medium);

        // technically still part of Given even though its called when
        when(planetRepositoryMock.save(planet)).thenReturn(planet);

        // When
        Planet savedPlanet = planetServiceMock.savePlanet(planet);

        // Then
        assertNotNull(savedPlanet, "The planet should not be null");
        assertEquals("Earth", savedPlanet.getPlanetName(), "The name of the planet should be Earth");
        assertEquals(PlanetSize.medium, savedPlanet.getPlanetSize(), "The size of the planet should be medium");
    }

    @Test
    public void given_planet_exists_when_delete_planet_return_void() {
        // Given
        Planet planet = new Planet();
        planet.setPlanetName("Earth");
        planet.setPlanetSize(PlanetSize.medium);

        // technically still part of Given even though its called when
        when(planetRepositoryMock.findById(1L)).thenReturn(Optional.of(planet));

        // When
        planetServiceMock.deletePlanet(1L);

        // Then
        // Verify that the deleteById method was called with the correct argument
        verify(planetRepositoryMock).deleteById(1L);
    }
}
