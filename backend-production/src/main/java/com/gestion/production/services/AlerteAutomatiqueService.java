package com.gestion.production.services;

import com.gestion.production.entities.Alerte;
import com.gestion.production.entities.Machine;
import com.gestion.production.entities.Produit;
import com.gestion.production.repositories.AlerteRepository;
import com.gestion.production.repositories.MachineRepository;
import com.gestion.production.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// @Service → dit à Spring que c'est un service
@Service
public class AlerteAutomatiqueService {

    @Autowired
    private AlerteRepository alerteRepository;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private ProduitRepository produitRepository;

    // @Scheduled → exécute automatiquement toutes les heures
    // cron = "0 0 * * * *" → chaque heure
    @Scheduled(cron = "* * * * * *")
    public void verifierAlertes() {
        verifierStockProduits();
        verifierMaintenanceMachines();
        verifierMachinesEnPanne();
    }

    // Vérifie les produits avec stock faible
    private void verifierStockProduits() {
        List<Produit> produits = produitRepository.findAll();
        for (Produit produit : produits) {
            if (produit.getStock() != null && produit.getStock() <= 10) {

                // Vérifie si une alerte existe déjà
                boolean existe = alerteRepository
                    .findByProduitId(produit.getId())
                    .stream()
                    .anyMatch(a ->
                        a.getType().equals("STOCK_BAS") &&
                        a.getStatut().equals("NON_LU"));

                if (!existe) {
                    Alerte alerte = new Alerte();
                    alerte.setType("STOCK_BAS");
                    alerte.setStatut("NON_LU");
                    alerte.setDateAlerte(LocalDateTime.now());
                    alerte.setProduit(produit);

                    if (produit.getStock() == 0) {
                        alerte.setMessage(
                            "⚠️ STOCK ÉPUISÉ : Le produit '"
                            + produit.getNom()
                            + "' est en rupture de stock !");
                    } else {
                        alerte.setMessage(
                            "⚠️ STOCK FAIBLE : Le produit '"
                            + produit.getNom()
                            + "' n'a plus que "
                            + produit.getStock()
                            + " unités en stock !");
                    }
                    alerteRepository.save(alerte);
                }
            }
        }
    }

    // Vérifie les machines avec maintenance prochaine
    private void verifierMaintenanceMachines() {
        List<Machine> machines = machineRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Machine machine : machines) {
            if (machine.getMaintenanceProchaine() != null) {
                long joursRestants = today.until(
                    machine.getMaintenanceProchaine(),
                    java.time.temporal.ChronoUnit.DAYS);

                // Alerte si maintenance dans moins de 7 jours
                if (joursRestants <= 7 && joursRestants >= 0) {

                    boolean existe = alerteRepository
                        .findByMachineId(machine.getId())
                        .stream()
                        .anyMatch(a ->
                            a.getType().equals("MAINTENANCE_REQUISE") &&
                            a.getStatut().equals("NON_LU"));

                    if (!existe) {
                        Alerte alerte = new Alerte();
                        alerte.setType("MAINTENANCE_REQUISE");
                        alerte.setStatut("NON_LU");
                        alerte.setDateAlerte(LocalDateTime.now());
                        alerte.setMachine(machine);
                        alerte.setMessage(
                            "🔧 MAINTENANCE REQUISE : La machine '"
                            + machine.getNom()
                            + "' nécessite une maintenance dans "
                            + joursRestants + " jour(s) !");
                        alerteRepository.save(alerte);
                    }
                }
            }
        }
    }

    // Vérifie les machines en panne
    private void verifierMachinesEnPanne() {
        List<Machine> machines = machineRepository
            .findByEtat("EN_PANNE");

        for (Machine machine : machines) {
            boolean existe = alerteRepository
                .findByMachineId(machine.getId())
                .stream()
                .anyMatch(a ->
                    a.getType().equals("PANNE") &&
                    a.getStatut().equals("NON_LU"));

            if (!existe) {
                Alerte alerte = new Alerte();
                alerte.setType("PANNE");
                alerte.setStatut("NON_LU");
                alerte.setDateAlerte(LocalDateTime.now());
                alerte.setMachine(machine);
                alerte.setMessage(
                    "🚨 PANNE DÉTECTÉE : La machine '"
                    + machine.getNom()
                    + "' est en panne ! Intervention requise.");
                alerteRepository.save(alerte);
            }
        }
    }

    // Méthode pour déclencher manuellement les alertes
    public void declencherAlertesManuellement() {
        verifierAlertes();
    }
}