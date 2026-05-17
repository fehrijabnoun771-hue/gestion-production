package com.gestion.production.services;

import com.gestion.production.entities.Technicien;
import com.gestion.production.repositories.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TechnicienService {

    @Autowired
    private TechnicienRepository technicienRepository;

    public List<Technicien> getAllTechniciens() {
        return technicienRepository.findAll();
    }

    public Optional<Technicien> getTechnicienById(Long id) {
        return technicienRepository.findById(id);
    }

    public Technicien createTechnicien(Technicien technicien) {
        return technicienRepository.save(technicien);
    }

    public Technicien updateTechnicien(Long id, Technicien technicien) {
        if (technicienRepository.existsById(id)) {
            technicien.setId(id);
            return technicienRepository.save(technicien);
        }
        return null;
    }

    public void deleteTechnicien(Long id) {
        technicienRepository.deleteById(id);
    }

    // Récupère les techniciens disponibles
    public List<Technicien> getTechniciensDisponibles() {
        return technicienRepository.findTechniciensDisponibles();
    }

    // Récupère les techniciens par compétence
    public List<Technicien> getTechniciensByCompetence(String competence) {
        return technicienRepository.findByCompetencesContaining(competence);
    }

    // Récupère les techniciens assignés à une machine
    public List<Technicien> getTechniciensByMachine(Long machineId) {
        return technicienRepository.findByMachineAssigneeId(machineId);
    }
}