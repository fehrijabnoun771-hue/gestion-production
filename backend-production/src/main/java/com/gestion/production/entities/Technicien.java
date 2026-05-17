package com.gestion.production.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// @Entity → cette classe représente la table "technicien" dans SQL Server
@Entity

// @Data → Lombok génère automatiquement getters, setters, toString...
@Data

// @Table → nom exact de la table dans SQL Server
@Table(name = "technicien")
public class Technicien {

    // Clé primaire auto-incrémentée
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank → le nom du technicien est obligatoire
    @NotBlank(message = "Le nom du technicien est obligatoire")
    @Column(name = "nom")
    private String nom;

    // compétences du technicien
    // ex: "Soudure, Électricité, Mécanique"
    @Column(name = "competences")
    private String competences;

    // téléphone du technicien
    @Column(name = "telephone")
    private String telephone;

    // @Email → vérifie le format email
    @Email(message = "Email invalide")
    @Column(name = "email")
    private String email;

    // @ManyToOne → plusieurs techniciens peuvent être
    // assignés à la même machine
    // @JoinColumn → correspond à machine_assignee_id dans SQL Server
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "machine_assignee_id")
   @JsonIgnoreProperties({"maintenances", "techniciens", 
    "ordresFabrication", "alertes", "hibernateLazyInitializer"})
    private Machine machineAssignee;

    // @OneToMany → un technicien peut avoir plusieurs maintenances
    // mappedBy = "technicien" → le champ dans Maintenance qui fait le lien
    // fetch = LAZY → charge les maintenances seulement si besoin
    @JsonIgnore
    @OneToMany(mappedBy = "technicien",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<Maintenance> maintenances;

    // @OneToMany → un technicien peut avoir plusieurs ordres de fabrication
    @JsonIgnore
    @OneToMany(mappedBy = "technicien",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<OrdreFabrication> ordresFabrication;
}