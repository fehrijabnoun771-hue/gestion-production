package com.gestion.production.repositories;

import com.gestion.production.entities.CategorieProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategorieProduitRepository 
    extends JpaRepository<CategorieProduit, Long> {

    // Trouve une catégorie par nom exact
    // SELECT * FROM categorie_produit WHERE nom = ?
    Optional<CategorieProduit> findByNom(String nom);

    // Trouve les catégories dont le nom contient un mot
    List<CategorieProduit> findByNomContaining(String nom);
}