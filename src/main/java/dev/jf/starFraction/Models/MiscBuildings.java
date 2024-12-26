package dev.jf.starFraction.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Misc_buildings")
public class MiscBuildings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="shipyard_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer shipyardLevel = 0;

    @Column(name="research_lab_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer researchLabLevel = 0;

    // setters must only allow values to be incremented by 1
    public void setShipyardLevel() {
        this.shipyardLevel = this.shipyardLevel++;
    }

    public void setResearchLabLevel() {
        this.researchLabLevel = this.researchLabLevel++;
    }
}
