package com.gestion.production.services;

import com.gestion.production.entities.Alerte;
import com.gestion.production.repositories.AlerteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AlerteService {

    @Autowired
    private AlerteRepository alerteRepository;

    public List<Alerte> getAllAlertes() {
        return alerteRepository.findAll();
    }

    public Optional<Alerte> getAlerteById(Long id) {
        return alerteRepository.findById(id);
    }

    public Alerte createAlerte(Alerte alerte) {
        return alerteRepository.save(alerte);
    }

    public Alerte updateAlerte(Long id, Alerte alerte) {
        if (alerteRepository.existsById(id)) {
            alerte.setId(id);
            return alerteRepository.save(alerte);
        }
        return null;
    }

    public void deleteAlerte(Long id) {
        alerteRepository.deleteById(id);
    }

    // Récupère les alertes non lues
    public List<Alerte> getAlertesNonLues() {
        return alerteRepository.findByStatut("NON_LU");
    }

    // Compte les alertes non lues
    public Long countAlertesNonLues() {
        return alerteRepository.countAlertesNonLues();
    }

    // Marque une alerte comme lue
    public Alerte marquerCommeLue(Long id) {
        Optional<Alerte> alerte = alerteRepository.findById(id);
        if (alerte.isPresent()) {
            alerte.get().setStatut("LU");
            return alerteRepository.save(alerte.get());
        }
        return null;
    }
}