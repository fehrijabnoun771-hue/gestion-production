package com.gestion.production.repositories;

import com.gestion.production.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProduitRepository 
    extends JpaRepository<Produit, Long> {

    // Trouve les produits par type
    List<Produit> findByType(String type);

    // Trouve les produits par nom
    List<Produit> findByNomContaining(String nom);

    // Trouve les produits dont le stock est inférieur à une valeur
    // SELECT * FROM produit WHERE stock < ?
    List<Produit> findByStockLessThan(Integer stock);

    // Trouve les produits d'un fournisseur
    // SELECT * FROM produit WHERE fournisseur_id = ?
    List<Produit> findByFournisseurId(Long fournisseurId);

    // Trouve les produits d'une catégorie
    List<Produit> findByCategorieId(Long categorieId);

    // Produits avec stock faible (alerte)
    @Query("SELECT p FROM Produit p WHERE p.stock <= 10")
    List<Produit> findProduitsStockFaible();
}