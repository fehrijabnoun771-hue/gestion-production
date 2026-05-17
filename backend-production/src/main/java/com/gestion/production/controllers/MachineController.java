package com.gestion.production.controllers;

import com.gestion.production.entities.Machine;
import com.gestion.production.services.MachineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// @RestController → dit à Spring que c'est un controller REST
// Retourne automatiquement du JSON
@RestController

// @RequestMapping → préfixe de toutes les URLs de ce controller
// Toutes les URLs commencent par /api/machines
@RequestMapping("/api/machines")

// @CrossOrigin → autorise Angular (port 4200) à appeler ce controller
// Sans ça → Angular ne peut pas appeler Spring Boot
@CrossOrigin(origins = "http://localhost:4200")
public class MachineController {

    @Autowired
    private MachineService machineService;

    // GET http://localhost:8080/api/machines
    // Retourne la liste de toutes les machines en JSON
    @GetMapping
    public ResponseEntity<List<Machine>> getAllMachines() {
        List<Machine> machines = machineService.getAllMachines();
        return ResponseEntity.ok(machines);
    }

    // GET http://localhost:8080/api/machines/1
    // Retourne une machine par son id
    @GetMapping("/{id}")
    public ResponseEntity<Machine> getMachineById(@PathVariable Long id) {
        return machineService.getMachineById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // POST http://localhost:8080/api/machines
    // Crée une nouvelle machine
    // @Valid → valide les données reçues (@NotBlank etc.)
    // @RequestBody → Spring convertit le JSON reçu en objet Machine
    @PostMapping
    public ResponseEntity<Machine> createMachine(
            @Valid @RequestBody Machine machine) {
        Machine created = machineService.createMachine(machine);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT http://localhost:8080/api/machines/1
    // Modifie une machine existante
    @PutMapping("/{id}")
    public ResponseEntity<Machine> updateMachine(
            @PathVariable Long id,
            @Valid @RequestBody Machine machine) {
        Machine updated = machineService.updateMachine(id, machine);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE http://localhost:8080/api/machines/1
    // Supprime une machine
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachine(@PathVariable Long id) {
        machineService.deleteMachine(id);
        return ResponseEntity.noContent().build();
    }

    // GET http://localhost:8080/api/machines/etat/DISPONIBLE
    // Retourne les machines par état
    @GetMapping("/etat/{etat}")
    public ResponseEntity<List<Machine>> getMachinesByEtat(
            @PathVariable String etat) {
        return ResponseEntity.ok(machineService.getMachinesByEtat(etat));
    }

    // GET http://localhost:8080/api/machines/maintenance
    // Retourne les machines qui ont besoin de maintenance
    @GetMapping("/maintenance")
    public ResponseEntity<List<Machine>> getMachinesNeedingMaintenance() {
        return ResponseEntity.ok(
            machineService.getMachinesNeedingMaintenance());
    }

    // GET http://localhost:8080/api/machines/search?keyword=CNC
    // Recherche les machines par nom
    @GetMapping("/search")
    public ResponseEntity<List<Machine>> searchMachines(
            @RequestParam String keyword) {
        return ResponseEntity.ok(machineService.searchMachines(keyword));
    }
}