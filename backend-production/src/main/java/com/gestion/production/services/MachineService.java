package com.gestion.production.services;

import com.gestion.production.entities.Machine;
import com.gestion.production.repositories.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// @Service → dit à Spring que c'est un service
// Spring va créer automatiquement une instance de cette classe
@Service
public class MachineService {

    // @Autowired → Spring injecte automatiquement le repository
    // Tu n'as pas besoin de créer l'objet manuellement
    // Spring fait : machineRepository = new MachineRepository()
    @Autowired
    private MachineRepository machineRepository;

    // Récupère toutes les machines
    // SELECT * FROM machine
    public List<Machine> getAllMachines() {
        return machineRepository.findAll();
    }

    // Récupère une machine par son id
    // SELECT * FROM machine WHERE id = ?
    public Optional<Machine> getMachineById(Long id) {
        return machineRepository.findById(id);
    }

    // Crée une nouvelle machine
    // INSERT INTO machine...
    public Machine createMachine(Machine machine) {
        return machineRepository.save(machine);
    }

    // Modifie une machine existante
    // UPDATE machine SET...
    public Machine updateMachine(Long id, Machine machine) {
        // Vérifie si la machine existe
        if (machineRepository.existsById(id)) {
            machine.setId(id);
            return machineRepository.save(machine);
        }
        return null;
    }

    // Supprime une machine
    // DELETE FROM machine WHERE id = ?
    public void deleteMachine(Long id) {
        machineRepository.deleteById(id);
    }

    // Récupère les machines par état
    public List<Machine> getMachinesByEtat(String etat) {
        return machineRepository.findByEtat(etat);
    }

    // Récupère les machines qui ont besoin de maintenance
    public List<Machine> getMachinesNeedingMaintenance() {
        return machineRepository.findMachinesNeedingMaintenance();
    }

    // Recherche les machines par nom
    public List<Machine> searchMachines(String keyword) {
        return machineRepository.findByNomContaining(keyword);
    }
}