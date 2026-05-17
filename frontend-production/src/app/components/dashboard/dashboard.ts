import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe, SlicePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import {
  Api, Machine, OrdreFabrication,
  Alerte, Maintenance, Produit
} from '../../services/api';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class Dashboard implements OnInit {

  today = new Date();

  machines: Machine[] = [];
  ordres: OrdreFabrication[] = [];
  alertes: Alerte[] = [];
  maintenances: Maintenance[] = [];
  produitsStockFaibleList: Produit[] = [];

  totalMachines = 0;
  machinesDisponibles = 0;
  machinesEnPanne = 0;
  totalTechniciens = 0;
  totalOrdres = 0;
  ordresEnCours = 0;
  totalAlertes = 0;
  totalProduits = 0;
  produitsStockFaible = 0;
  totalMaintenances = 0;
  maintenancesPlanifiees = 0;
  totalFournisseurs = 0;

  constructor(private api: Api) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    // Machines
    this.api.getMachines().subscribe(data => {
      this.machines = data;
      this.totalMachines = data.length;
      this.machinesDisponibles = data.filter(
        m => m.etat === 'DISPONIBLE').length;
      this.machinesEnPanne = data.filter(
        m => m.etat === 'EN_PANNE').length;
    });

    // Techniciens
    this.api.getTechniciens().subscribe(data => {
      this.totalTechniciens = data.length;
    });

    // Ordres
    this.api.getOrdres().subscribe(data => {
      this.ordres = data;
      this.totalOrdres = data.length;
      this.ordresEnCours = data.filter(
        o => o.statut === 'EN_COURS').length;
    });

    // Alertes
    this.api.getAlertesNonLues().subscribe(data => {
      this.alertes = data;
      this.totalAlertes = data.length;
    });

    // Maintenances
    this.api.getMaintenances().subscribe(data => {
      this.maintenances = data;
      this.totalMaintenances = data.length;
      this.maintenancesPlanifiees = data.filter(
        m => m.statut === 'PLANIFIEE').length;
    });

    // Produits
    this.api.getProduits().subscribe(data => {
      this.totalProduits = data.length;
      this.produitsStockFaibleList = data.filter(
        p => p.stock !== undefined && p.stock <= 10);
      this.produitsStockFaible = this.produitsStockFaibleList.length;
    });

    // Fournisseurs
    this.api.getFournisseurs().subscribe(data => {
      this.totalFournisseurs = data.length;
    });
  }

  isMaintenanceSoon(machine: Machine): boolean {
    if (!machine.maintenanceProchaine) return false;
    const today = new Date();
    const date = new Date(machine.maintenanceProchaine);
    const diff = (date.getTime() - today.getTime())
      / (1000 * 60 * 60 * 24);
    return diff <= 30 && diff >= 0;
  }
}