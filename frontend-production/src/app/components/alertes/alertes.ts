import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Api, Alerte, Machine, Produit } from '../../services/api';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-alertes',
  imports: [CommonModule, FormsModule],
  templateUrl: './alertes.html',
  styleUrl: './alertes.scss'
})
export class Alertes implements OnInit {

  alertes: Alerte[] = [];
  filteredAlertes: Alerte[] = [];
  machines: Machine[] = [];
  produits: Produit[] = [];
  currentAlerte: Alerte = { type: '', message: '' };
  selectedMachineId: number | null = null;
  selectedProduitId: number | null = null;
  showForm = false;
  submitted = false;
  searchKeyword = '';
  filterType = '';
  filterStatut = '';

constructor(private api: Api, private http: HttpClient) {}
  ngOnInit() {
    this.loadAlertes();
    this.loadMachines();
    this.loadProduits();
  }

  loadAlertes() {
    this.api.getAlertes().subscribe({
      next: (data) => {
        this.alertes = data;
        this.filteredAlertes = [...data];
      },
      error: (err) => console.error('Erreur:', err)
    });
  }

  loadMachines() {
    this.api.getMachines().subscribe(data => {
      this.machines = data;
    });
  }

  loadProduits() {
    this.api.getProduits().subscribe(data => {
      this.produits = data;
    });
  }

  filterAlertes() {
    let filtered = this.alertes;

    if (this.searchKeyword.trim()) {
      filtered = filtered.filter(a =>
        a.message.toLowerCase().includes(
          this.searchKeyword.toLowerCase()) ||
        a.type.toLowerCase().includes(
          this.searchKeyword.toLowerCase()));
    }

    if (this.filterType) {
      filtered = filtered.filter(
        a => a.type === this.filterType);
    }

    if (this.filterStatut) {
      filtered = filtered.filter(
        a => a.statut === this.filterStatut);
    }

    this.filteredAlertes = filtered;
  }

  resetFilters() {
    this.searchKeyword = '';
    this.filterType = '';
    this.filterStatut = '';
    this.filteredAlertes = [...this.alertes];
  }

  countNonLues(): number {
    return this.alertes.filter(
      a => a.statut === 'NON_LU').length;
  }

  countByStatut(statut: string): number {
    return this.alertes.filter(
      a => a.statut === statut).length;
  }

  openForm() {
    this.showForm = true;
    this.submitted = false;
    this.currentAlerte = {
      type: '',
      message: '',
      statut: 'NON_LU'
    };
    this.selectedMachineId = null;
    this.selectedProduitId = null;
  }

  onMachineChange() {
    if (this.selectedMachineId) {
      this.currentAlerte.machine = {
        id: this.selectedMachineId, nom: ''
      };
    } else {
      this.currentAlerte.machine = undefined;
    }
  }

  onProduitChange() {
    if (this.selectedProduitId) {
      this.currentAlerte.produit = {
        id: this.selectedProduitId, nom: ''
      };
    } else {
      this.currentAlerte.produit = undefined;
    }
  }

  saveAlerte() {
    this.submitted = true;
    if (!this.currentAlerte.type) return;
    if (!this.currentAlerte.message) return;

    this.api.createAlerte(this.currentAlerte).subscribe({
      next: () => {
        this.cancelForm();
        this.loadAlertes();
        alert('Alerte créée avec succès !');
      },
      error: (err) => {
        console.error('Erreur:', err);
        alert('Erreur lors de la création !');
      }
    });
  }

  marquerLue(id: number) {
    this.api.marquerAlerteLue(id).subscribe({
      next: () => this.loadAlertes(),
      error: (err) => console.error('Erreur:', err)
    });
  }

  marquerTraite(alerte: Alerte) {
    this.api.updateAlerte(alerte.id!, {
      ...alerte, statut: 'TRAITE'
    }).subscribe({
      next: () => this.loadAlertes(),
      error: (err) => console.error('Erreur:', err)
    });
  }

  marquerToutesLues() {
    const nonLues = this.alertes.filter(
      a => a.statut === 'NON_LU');
    nonLues.forEach(alerte => {
      this.api.marquerAlerteLue(alerte.id!).subscribe();
    });
    setTimeout(() => this.loadAlertes(), 500);
    alert('Toutes les alertes marquées comme lues !');
  }

  deleteAlerte(id: number) {
    if (confirm('Voulez-vous supprimer cette alerte ?')) {
      this.api.deleteAlerte(id).subscribe({
        next: () => {
          this.loadAlertes();
          alert('Alerte supprimée !');
        },
        error: (err) => console.error('Erreur:', err)
      });
    }
  }

  cancelForm() {
    this.showForm = false;
    this.submitted = false;
    this.currentAlerte = { type: '', message: '' };
    this.selectedMachineId = null;
    this.selectedProduitId = null;
  }


  verifierAlertes() {
  this.http.post(
    'http://localhost:8080/api/alertes/verifier', {})
    .subscribe({
      next: () => {
        this.loadAlertes();
        alert('Vérification effectuée !');
      },
      error: (err) => console.error('Erreur:', err)
    });
}

}




