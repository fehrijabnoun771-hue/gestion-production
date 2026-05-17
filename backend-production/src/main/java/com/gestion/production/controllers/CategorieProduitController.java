package com.gestion.production.controllers;

import com.gestion.production.entities.CategorieProduit;
import com.gestion.production.services.CategorieProduitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategorieProduitController {

    @Autowired
    private CategorieProduitService categorieProduitService;

    @GetMapping
    public ResponseEntity<List<CategorieProduit>> getAllCategories() {
        return ResponseEntity.ok(categorieProduitService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieProduit> getCategorieById(
            @PathVariable Long id) {
        return categorieProduitService.getCategorieById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategorieProduit> createCategorie(
            @Valid @RequestBody CategorieProduit categorie) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(categorieProduitService.createCategorie(categorie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorieProduit> updateCategorie(
            @PathVariable Long id,
            @Valid @RequestBody CategorieProduit categorie) {
        CategorieProduit updated = categorieProduitService
            .updateCategorie(id, categorie);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        categorieProduitService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategorieProduit>> searchCategories(
            @RequestParam String nom) {
        return ResponseEntity.ok(
            categorieProduitService.searchCategories(nom));
    }
}