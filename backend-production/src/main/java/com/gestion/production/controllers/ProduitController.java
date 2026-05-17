package com.gestion.production.controllers;

import com.gestion.production.entities.Produit;
import com.gestion.production.services.ProduitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "http://localhost:4200")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {
        return ResponseEntity.ok(produitService.getAllProduits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        return produitService.getProduitById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produit> createProduit(
            @Valid @RequestBody Produit produit) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(produitService.createProduit(produit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(
            @PathVariable Long id,
            @Valid @RequestBody Produit produit) {
        Produit updated = produitService.updateProduit(id, produit);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stock-faible")
    public ResponseEntity<List<Produit>> getProduitsStockFaible() {
        return ResponseEntity.ok(produitService.getProduitsStockFaible());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Produit>> searchProduits(
            @RequestParam String nom) {
        return ResponseEntity.ok(produitService.searchProduits(nom));
    }

    @GetMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<List<Produit>> getProduitsByFournisseur(
            @PathVariable Long fournisseurId) {
        return ResponseEntity.ok(
            produitService.getProduitsByFournisseur(fournisseurId));
    }
}