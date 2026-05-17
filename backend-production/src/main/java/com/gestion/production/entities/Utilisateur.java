package com.gestion.production.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;


// @Entity → cette classe représente la table "utilisateur" dans SQL Server
@Entity

// @Data → Lombok génère automatiquement getters, setters, toString...
@Data

// @Table → nom exact de la table dans SQL Server
@Table(name = "utilisateur")
public class Utilisateur {

    // Clé primaire auto-incrémentée
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank → le nom est obligatoire
    @NotBlank(message = "Le nom est obligatoire")
    @Column(name = "nom")
    private String nom;

    // @Email → vérifie le format email
    // @Column(unique = true) → email unique dans la table
    // Si deux utilisateurs ont le même email → erreur
    @Email(message = "Email invalide")
    @NotBlank(message = "Email est obligatoire")
    @Column(name = "email", unique = true)
    private String email;

    // @NotBlank → mot de passe obligatoire
    // Dans le DTO on ne retournera jamais ce champ à Angular
    @NotBlank(message = "Mot de passe obligatoire")
    @Column(name = "mot_de_passe")
    private String motDePasse;

    // role → ADMIN, TECHNICIEN, OPERATEUR etc.
    @NotBlank(message = "Le role est obligatoire")
    @Column(name = "role")
    private String role;

    // actif → true = compte actif / false = compte désactivé
    // correspond au type BIT dans SQL Server
    @Column(name = "actif")
    private Boolean actif = true;

    // date de création du compte
    @Column(name = "date_creation")
    private LocalDate dateCreation;

    // @OneToMany → un utilisateur peut avoir plusieurs historiques
    // mappedBy = "utilisateur" → le champ dans HistoriqueProduction qui fait le lien
    // fetch = LAZY → charge l'historique seulement si besoin
    @JsonIgnore
    @OneToMany(mappedBy = "utilisateur",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<HistoriqueProduction> historiques;

    // @PrePersist → exécuté automatiquement AVANT l'insertion dans la DB
    // Met la date de création automatiquement
    @PrePersist
    public void prePersist() {
        if (dateCreation == null) {
            dateCreation = LocalDate.now();
        }
        if (actif == null) {
            actif = true;
        }
    }
}