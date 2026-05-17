package com.gestion.production.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "fournisseur")
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du fournisseur est obligatoire")
    @Column(name = "nom")
    private String nom;

    @Column(name = "contact")
    private String contact;

    @Column(name = "telephone")
    private String telephone;

    @Email(message = "Email invalide")
    @Column(name = "email")
    private String email;

    @Column(name = "adresse")
    private String adresse;

    @Min(value = 0, message = "La note minimum est 0")
    @Max(value = 5, message = "La note maximum est 5")
    @Column(name = "note")
    private Integer note = 0;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @JsonIgnore
    @OneToMany(mappedBy = "fournisseur", fetch = FetchType.LAZY)
    private List<Produit> produits;

    @JsonIgnore
    @OneToMany(mappedBy = "fournisseur", fetch = FetchType.LAZY)
    private List<PieceRechange> pieces;

    @PrePersist
    public void prePersist() {
        if (dateCreation == null) {
            dateCreation = LocalDate.now();
        }
    }
}