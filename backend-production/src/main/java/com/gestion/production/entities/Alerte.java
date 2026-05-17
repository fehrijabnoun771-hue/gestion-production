package com.gestion.production.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

// @Entity → représente la table "alerte"
@Entity
@Data
@Table(name = "alerte")
public class Alerte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // type d'alerte
    // ex: STOCK_BAS, MAINTENANCE_REQUISE, PANNE
    @NotBlank(message = "Le type est obligatoire")
    @Column(name = "type")
    private String type;

    // message de l'alerte
    @NotBlank(message = "Le message est obligatoire")
    @Column(name = "message")
    private String message;

    // LocalDateTime → correspond à DATETIME dans SQL Server
    // contient date + heure ex: 2026-05-03 14:30:00
    @Column(name = "date_alerte")
    private LocalDateTime dateAlerte;

    // statut de l'alerte
    // ex: NON_LU, LU, TRAITE
    @Column(name = "statut")
    private String statut = "NON_LU";

    // @ManyToOne → plusieurs alertes peuvent concerner la même machine
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    // @ManyToOne → plusieurs alertes peuvent concerner le même produit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id")
    private Produit produit;

    // @PrePersist → met la date automatiquement avant insertion
    @PrePersist
    public void prePersist() {
        if (dateAlerte == null) {
            dateAlerte = LocalDateTime.now();
        }
    }
}