package com.gestion.production.repositories;

import com.gestion.production.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FournisseurRepository 
    extends JpaRepository<Fournisseur, Long> {

    // Trouve un fournisseur par email
    // SELECT * FROM fournisseur WHERE email = ?
    Optional<Fournisseur> findByEmail(String email);

    // Trouve les fournisseurs par nom
    List<Fournisseur> findByNomContaining(String nom);

    // Trouve les fournisseurs avec une note >= valeur
    // SELECT * FROM fournisseur WHERE note >= ?
    List<Fournisseur> findByNoteGreaterThanEqual(Integer note);
}