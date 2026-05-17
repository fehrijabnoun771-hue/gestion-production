package com.gestion.production.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

// @Entity → cette classe représente la table "produit" dans SQL Server
@Entity

// @Data → Lombok génère automatiquement getters, setters, toString...
@Data

// @Table → nom exact de la table dans SQL Server
@Table(name = "produit")
public class Produit {

    // Clé primaire auto-incrémentée
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank → le nom du produit est obligatoire
    @NotBlank(message = "Le nom du produit est obligatoire")
    @Column(name = "nom")
    private String nom;

    // type du produit ex: MATIERE_PREMIERE, PRODUIT_FINI etc.
    @Column(name = "type")
    private String type;

    // @Min(0) → le stock ne peut pas être négatif
    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    @Column(name = "stock")
    private Integer stock = 0;

    // BigDecimal → correspond au type DECIMAL(10,2) dans SQL Server
    // Utilisé pour les prix car plus précis que Double
    // Exemple : 99.99
    @Column(name = "prix")
    private BigDecimal prix;

    // @ManyToOne → plusieurs produits peuvent avoir le même fournisseur
    // @JoinColumn → nom de la clé étrangère dans la table produit
    // fournisseur_id dans SQL = fournisseur en Java
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    // @ManyToOne → plusieurs produits peuvent avoir la même catégorie
    // @JoinColumn → nom de la clé étrangère dans la table produit
    // categorie_id dans SQL = categorie en Java
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    private CategorieProduit categorie;

    // @OneToMany → un produit peut avoir plusieurs ordres de fabrication
    // mappedBy = "produit" → le champ dans OrdreFabrication qui fait le lien
    @JsonIgnore
    @OneToMany(mappedBy = "produit",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<OrdreFabrication> ordresFabrication;

    // @OneToMany → un produit peut avoir plusieurs alertes
    @JsonIgnore
    @OneToMany(mappedBy = "produit",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<Alerte> alertes;
}