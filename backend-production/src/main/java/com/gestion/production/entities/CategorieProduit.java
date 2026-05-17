package com.gestion.production.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;


// @Entity → cette classe représente la table "categorie_produit" dans SQL Server
@Entity

// @Data → Lombok génère automatiquement getters, setters, toString...
@Data

// @Table → nom exact de la table dans SQL Server
@Table(name = "categorie_produit")
public class CategorieProduit {

    // Clé primaire auto-incrémentée
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank → le nom de la catégorie est obligatoire
    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    @Column(name = "nom")
    private String nom;

    // description peut être null
    @Column(name = "description")
    private String description;

    // @OneToMany → une catégorie peut avoir plusieurs produits
    // mappedBy = "categorie" → le champ dans Produit.java qui fait le lien
    // cascade = ALL → si on supprime une catégorie → ses produits sont supprimés
    // fetch = LAZY → charge les produits seulement quand on en a besoin
    // (pas automatiquement à chaque fois qu'on charge une catégorie)
    @JsonIgnore
    @OneToMany(mappedBy = "categorie", 
               cascade = CascadeType.ALL, 
               fetch = FetchType.LAZY)
    private List<Produit> produits;
}