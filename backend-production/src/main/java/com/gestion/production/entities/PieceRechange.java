package com.gestion.production.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;


// @Entity → représente la table "piece_rechange"
@Entity
@Data
@Table(name = "piece_rechange")
public class PieceRechange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nom de la pièce obligatoire
    @NotBlank(message = "Le nom de la pièce est obligatoire")
    @Column(name = "nom")
    private String nom;

    // référence unique de la pièce
    @NotBlank(message = "La référence est obligatoire")
    @Column(name = "reference")
    private String reference;

    // @Min(0) → stock ne peut pas être négatif
    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    @Column(name = "stock")
    private Integer stock = 0;

    // prix de la pièce
    // BigDecimal → correspond à DECIMAL(10,2) dans SQL Server
    @Column(name = "prix")
    private BigDecimal prix;

    // @ManyToOne → plusieurs pièces peuvent venir du même fournisseur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    // @OneToMany → une pièce peut être utilisée dans plusieurs maintenances
    @JsonIgnore
    @OneToMany(mappedBy = "piece",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<MaintenancePiece> maintenances;
}