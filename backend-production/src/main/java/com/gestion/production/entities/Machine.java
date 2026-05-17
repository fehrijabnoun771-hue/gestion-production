package com.gestion.production.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

// @Entity → représente la table "machine"
@Entity
@Data
@Table(name = "machine")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(name = "nom")
    private String nom;

    // état de la machine
    // ex: DISPONIBLE, EN_MAINTENANCE, EN_PANNE, ARRETEE
    @Column(name = "etat")
    private String etat;

    @Column(name = "maintenance_prochaine")
    private LocalDate maintenanceProchaine;

    @Column(name = "date_acquisition")
    private LocalDate dateAcquisition;

    @Column(name = "description")
    private String description;

    // @OneToMany → une machine peut avoir plusieurs maintenances
    @JsonIgnore
    @OneToMany(mappedBy = "machine",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<Maintenance> maintenances;

    // @OneToMany → une machine peut avoir plusieurs techniciens assignés
    @JsonIgnore
    @OneToMany(mappedBy = "machineAssignee",
               fetch = FetchType.LAZY)
    private List<Technicien> techniciens;

    // @OneToMany → une machine peut avoir plusieurs ordres de fabrication
    @JsonIgnore
    @OneToMany(mappedBy = "machine",
               fetch = FetchType.LAZY)
    private List<OrdreFabrication> ordresFabrication;

    // @OneToMany → une machine peut avoir plusieurs alertes
    @JsonIgnore
    @OneToMany(mappedBy = "machine",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<Alerte> alertes;
}