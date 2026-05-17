package com.gestion.production.controllers;

import com.gestion.production.entities.MaintenancePiece;
import com.gestion.production.services.MaintenancePieceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance-pieces")
@CrossOrigin(origins = "http://localhost:4200")
public class MaintenancePieceController {

    @Autowired
    private MaintenancePieceService maintenancePieceService;

    @GetMapping
    public ResponseEntity<List<MaintenancePiece>> getAllMaintenancePieces() {
        return ResponseEntity.ok(
            maintenancePieceService.getAllMaintenancePieces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenancePiece> getMaintenancePieceById(
            @PathVariable Long id) {
        return maintenancePieceService.getMaintenancePieceById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MaintenancePiece> createMaintenancePiece(
            @Valid @RequestBody MaintenancePiece mp) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(maintenancePieceService.createMaintenancePiece(mp));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenancePiece> updateMaintenancePiece(
            @PathVariable Long id,
            @Valid @RequestBody MaintenancePiece mp) {
        MaintenancePiece updated = maintenancePieceService
            .updateMaintenancePiece(id, mp);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenancePiece(@PathVariable Long id) {
        maintenancePieceService.deleteMaintenancePiece(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/maintenance/{maintenanceId}")
    public ResponseEntity<List<MaintenancePiece>> getPiecesByMaintenance(
            @PathVariable Long maintenanceId) {
        return ResponseEntity.ok(
            maintenancePieceService.getPiecesByMaintenance(maintenanceId));
    }
}