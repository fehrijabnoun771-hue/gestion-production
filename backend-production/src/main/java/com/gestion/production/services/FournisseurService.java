package com.gestion.production.services;

import com.gestion.production.entities.Fournisseur;
import com.gestion.production.repositories.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    // Récupère tous les fournisseurs
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    // Récupère un fournisseur par id
    public Optional<Fournisseur> getFournisseurById(Long id) {
        return fournisseurRepository.findById(id);
    }

    // Crée un nouveau fournisseur
    public Fournisseur createFournisseur(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    // Modifie un fournisseur
    public Fournisseur updateFournisseur(Long id, Fournisseur fournisseur) {
        if (fournisseurRepository.existsById(id)) {
            fournisseur.setId(id);
            return fournisseurRepository.save(fournisseur);
        }
        return null;
    }

    // Supprime un fournisseur
    public void deleteFournisseur(Long id) {
        fournisseurRepository.deleteById(id);
    }

    // Récupère les fournisseurs par note minimum
    public List<Fournisseur> getFournisseursByNote(Integer note) {
        return fournisseurRepository.findByNoteGreaterThanEqual(note);
    }

    // Recherche les fournisseurs par nom
    public List<Fournisseur> searchFournisseurs(String nom) {
        return fournisseurRepository.findByNomContaining(nom);
    }
}