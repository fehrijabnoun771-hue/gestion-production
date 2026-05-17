package com.gestion.production.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// @Entity → représente la table "maintenance_piece"
// C'est la table de liaison entre maintenance et piece_rechange
@Entity
@Data
@Table(name = "maintenance_piece")
public class MaintenancePiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne → plusieurs enregistrements peuvent concerner
    // la même maintenance
    @NotNull(message = "La maintenance est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_id")
    private Maintenance maintenance;

    // @ManyToOne → plusieurs enregistrements peuvent concerner
    // la même pièce
    @NotNull(message = "La pièce est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piece_id")
    private PieceRechange piece;

    // quantité de pièces utilisées dans cette maintenance
    @Min(value = 1, message = "La quantité doit être au moins 1")
    @Column(name = "quantite_utilisee")
    private Integer quantiteUtilisee;
}