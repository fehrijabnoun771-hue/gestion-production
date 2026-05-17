package com.gestion.production.services;

import com.gestion.production.entities.CategorieProduit;
import com.gestion.production.repositories.CategorieProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategorieProduitService {

    @Autowired
    private CategorieProduitRepository categorieProduitRepository;

    public List<CategorieProduit> getAllCategories() {
        return categorieProduitRepository.findAll();
    }

    public Optional<CategorieProduit> getCategorieById(Long id) {
        return categorieProduitRepository.findById(id);
    }

    public CategorieProduit createCategorie(CategorieProduit categorie) {
        return categorieProduitRepository.save(categorie);
    }

    public CategorieProduit updateCategorie(Long id, CategorieProduit categorie) {
        if (categorieProduitRepository.existsById(id)) {
            categorie.setId(id);
            return categorieProduitRepository.save(categorie);
        }
        return null;
    }

    public void deleteCategorie(Long id) {
        categorieProduitRepository.deleteById(id);
    }

    public List<CategorieProduit> searchCategories(String nom) {
        return categorieProduitRepository.findByNomContaining(nom);
    }
}