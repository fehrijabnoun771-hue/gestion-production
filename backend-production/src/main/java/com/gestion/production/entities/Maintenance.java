package com.gestion.production.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@Table(name = "maintenance")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FetchType.EAGER → charge la machine automatiquement
    // @JsonIgnoreProperties → évite la boucle infinie JSON
    @NotNull(message = "La machine est obligatoire")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "machine_id")
    @JsonIgnoreProperties({"maintenances", "techniciens",
        "ordresFabrication", "alertes",
        "hibernateLazyInitializer"})
    private Machine machine;

    // FetchType.EAGER → charge le technicien automatiquement
    @NotNull(message = "Le technicien est obligatoire")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "technicien_id")
    @JsonIgnoreProperties({"maintenances", "ordresFabrication",
        "machineAssignee", "hibernateLazyInitializer"})
    private Technicien technicien;

    @NotNull(message = "La date de maintenance est obligatoire")
    @Column(name = "date_maintenance")
    private LocalDate dateMaintenance;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "cout")
    private BigDecimal cout;

    @Column(name = "statut")
    private String statut = "PLANIFIEE";

    @JsonIgnore
    @OneToMany(mappedBy = "maintenance",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<MaintenancePiece> pieces;
}