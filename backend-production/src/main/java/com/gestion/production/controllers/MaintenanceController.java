package com.gestion.production.controllers;

import com.gestion.production.entities.Maintenance;
import com.gestion.production.services.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@CrossOrigin(origins = "http://localhost:4200")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<Maintenance>> getAllMaintenances() {
        return ResponseEntity.ok(maintenanceService.getAllMaintenances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getMaintenanceById(
            @PathVariable Long id) {
        return maintenanceService.getMaintenanceById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Maintenance> createMaintenance(
            @Valid @RequestBody Maintenance maintenance) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(maintenanceService.createMaintenance(maintenance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> updateMaintenance(
            @PathVariable Long id,
            @Valid @RequestBody Maintenance maintenance) {
        Maintenance updated = maintenanceService
            .updateMaintenance(id, maintenance);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<Maintenance>> getMaintenancesByMachine(
            @PathVariable Long machineId) {
        return ResponseEntity.ok(
            maintenanceService.getMaintenancesByMachine(machineId));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Maintenance>> getMaintenancesByStatut(
            @PathVariable String statut) {
        return ResponseEntity.ok(
            maintenanceService.getMaintenancesByStatut(statut));
    }

    @GetMapping("/aujourd-hui")
    public ResponseEntity<List<Maintenance>> getMaintenancesAujourdhui() {
        return ResponseEntity.ok(
            maintenanceService.getMaintenancesAujourdhui());
    }

    // GET /api/maintenances/periode?debut=2026-01-01&fin=2026-12-31
    @GetMapping("/periode")
    public ResponseEntity<List<Maintenance>> getMaintenancesByPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fin) {
        return ResponseEntity.ok(
            maintenanceService.getMaintenancesByPeriode(debut, fin));
    }
}