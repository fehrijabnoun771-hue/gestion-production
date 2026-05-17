package com.gestion.production.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

// @Entity → représente la table "historique_production"
@Entity
@Data
@Table(name = "historique_production")
public class HistoriqueProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne → plusieurs historiques peuvent concerner
    // le même ordre de fabrication
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordre_id")
    private OrdreFabrication ordre;

    // action effectuée
    // ex: CREATION, MODIFICATION, ANNULATION, COMPLETION
    @NotBlank(message = "L'action est obligatoire")
    @Column(name = "action")
    private String action;

    // description détaillée de l'action
    @Column(name = "description")
    private String description;

    // date et heure de l'action
    // LocalDateTime → correspond à DATETIME dans SQL Server
    @Column(name = "date_action")
    private LocalDateTime dateAction;

    // @ManyToOne → plusieurs historiques peuvent être créés
    // par le même utilisateur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    // @PrePersist → met la date automatiquement avant insertion
    @PrePersist
    public void prePersist() {
        if (dateAction == null) {
            dateAction = LocalDateTime.now();
        }
    }
}