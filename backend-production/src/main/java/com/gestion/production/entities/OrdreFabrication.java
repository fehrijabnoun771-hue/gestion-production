package com.gestion.production.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

// @Entity → représente la table "ordre_fabrication"
@Entity
@Data
@Table(name = "ordre_fabrication")
public class OrdreFabrication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotNull → le produit est obligatoire
    // @ManyToOne → plusieurs ordres peuvent concerner le même produit
    @NotNull(message = "Le produit est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id")
    private Produit produit;

    // @Min(1) → la quantité doit être au moins 1
    @Min(value = 1, message = "La quantité doit être au moins 1")
    @Column(name = "quantite")
    private Integer quantite;

    // date de création de l'ordre
    @NotNull(message = "La date est obligatoire")
    @Column(name = "date_creation")
    private LocalDate dateCreation;

    // date de fin prévue
    @Column(name = "date_fin")
    private LocalDate dateFin;

    // @ManyToOne → plusieurs ordres peuvent utiliser la même machine
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    // @ManyToOne → plusieurs ordres peuvent être assignés au même technicien
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technicien_id")
    private Technicien technicien;

    // statut de l'ordre
    // ex: EN_ATTENTE, EN_COURS, TERMINE, ANNULE
    @Column(name = "statut")
    private String statut = "EN_ATTENTE";

    // priorité de l'ordre
    // ex: BASSE, NORMALE, HAUTE, URGENTE
    @Column(name = "priorite")
    private String priorite = "NORMALE";

    // @OneToMany → un ordre peut avoir plusieurs historiques
    @JsonIgnore
    @OneToMany(mappedBy = "ordre",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<HistoriqueProduction> historiques;

    // @PrePersist → met la date automatiquement avant insertion
    @PrePersist
    public void prePersist() {
        if (dateCreation == null) {
            dateCreation = LocalDate.now();
        }
    }
}