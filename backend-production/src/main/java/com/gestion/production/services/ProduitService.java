package com.gestion.production.services;

import com.gestion.production.entities.Produit;
import com.gestion.production.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    public Optional<Produit> getProduitById(Long id) {
        return produitRepository.findById(id);
    }

    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    public Produit updateProduit(Long id, Produit produit) {
        if (produitRepository.existsById(id)) {
            produit.setId(id);
            return produitRepository.save(produit);
        }
        return null;
    }

    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }

    // Récupère les produits avec stock faible
    public List<Produit> getProduitsStockFaible() {
        return produitRepository.findProduitsStockFaible();
    }

    // Récupère les produits par fournisseur
    public List<Produit> getProduitsByFournisseur(Long fournisseurId) {
        return produitRepository.findByFournisseurId(fournisseurId);
    }

    // Recherche les produits par nom
    public List<Produit> searchProduits(String nom) {
        return produitRepository.findByNomContaining(nom);
    }
}