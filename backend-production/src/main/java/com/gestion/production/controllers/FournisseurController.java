package com.gestion.production.controllers;

import com.gestion.production.entities.Fournisseur;
import com.gestion.production.services.FournisseurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
@CrossOrigin(origins = "http://localhost:4200")
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    @GetMapping
    public ResponseEntity<List<Fournisseur>> getAllFournisseurs() {
        return ResponseEntity.ok(fournisseurService.getAllFournisseurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(
            @PathVariable Long id) {
        return fournisseurService.getFournisseurById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Fournisseur> createFournisseur(
            @Valid @RequestBody Fournisseur fournisseur) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(fournisseurService.createFournisseur(fournisseur));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(
            @PathVariable Long id,
            @Valid @RequestBody Fournisseur fournisseur) {
        Fournisseur updated = fournisseurService.updateFournisseur(id, fournisseur);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Fournisseur>> searchFournisseurs(
            @RequestParam String nom) {
        return ResponseEntity.ok(fournisseurService.searchFournisseurs(nom));
    }

    @GetMapping("/note/{note}")
    public ResponseEntity<List<Fournisseur>> getFournisseursByNote(
            @PathVariable Integer note) {
        return ResponseEntity.ok(
            fournisseurService.getFournisseursByNote(note));
    }
}