import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  Api, OrdreFabrication, Produit, Machine, Technicien
} from '../../services/api';

@Component({
  selector: 'app-ordres',
  imports: [CommonModule, FormsModule],
  templateUrl: './ordres.html',
  styleUrl: './ordres.scss'
})
export class Ordres implements OnInit {

  ordres: OrdreFabrication[] = [];
  filteredOrdres: OrdreFabrication[] = [];
  produits: Produit[] = [];
  machines: Machine[] = [];
  techniciens: Technicien[] = [];
  currentOrdre: OrdreFabrication = { quantite: 0 };
  selectedOrdre: OrdreFabrication | null = null;
  selectedProduitId: number | null = null;
  selectedMachineId: number | null = null;
  selectedTechnicienId: number | null = null;
  showForm = false;
  editMode = false;
  submitted = false;
  searchKeyword = '';
  filterStatut = '';
  filterPriorite = '';

  constructor(private api: Api) {}

  ngOnInit() {
    this.loadOrdres();
    this.loadProduits();
    this.loadMachines();
    this.loadTechniciens();
  }

  loadOrdres() {
    this.api.getOrdres().subscribe({
      next: (data) => {
        this.ordres = data;
        this.filteredOrdres = [...data];
      },
      error: (err) => console.error('Erreur:', err)
    });
  }

  loadProduits() {
    this.api.getProduits().subscribe(data => {
      this.produits = data;
    });
  }

  loadMachines() {
    this.api.getMachines().subscribe(data => {
      this.machines = data;
    });
  }

  loadTechniciens() {
    this.api.getTechniciens().subscribe(data => {
      this.techniciens = data;
    });
  }

  filterOrdres() {
    let filtered = this.ordres;

    if (this.searchKeyword.trim()) {
      filtered = filtered.filter(o =>
        o.produit?.nom?.toLowerCase().includes(
          this.searchKeyword.toLowerCase()));
    }

    if (this.filterStatut) {
      filtered = filtered.filter(
        o => o.statut === this.filterStatut);
    }

    if (this.filterPriorite) {
      filtered = filtered.filter(
        o => o.priorite === this.filterPriorite);
    }

    this.filteredOrdres = filtered;
  }

  resetFilters() {
    this.searchKeyword = '';
    this.filterStatut = '';
    this.filterPriorite = '';
    this.filteredOrdres = [...this.ordres];
  }

  countByStatut(statut: string): number {
    return this.ordres.filter(o => o.statut === statut).length;
  }

  openForm() {
    this.showForm = true;
    this.editMode = false;
    this.submitted = false;
    this.currentOrdre = {
      quantite: 1,
      statut: 'EN_ATTENTE',
      priorite: 'NORMALE'
    };
    this.selectedProduitId = null;
    this.selectedMachineId = null;
    this.selectedTechnicienId = null;
  }

  editOrdre(ordre: OrdreFabrication) {
    this.currentOrdre = { ...ordre };
    this.selectedProduitId = ordre.produit?.id || null;
    this.selectedMachineId = ordre.machine?.id || null;
    this.selectedTechnicienId = ordre.technicien?.id || null;
    this.showForm = true;
    this.editMode = true;
    this.submitted = false;
  }

  viewOrdre(ordre: OrdreFabrication) {
    this.selectedOrdre = ordre;
  }

  closeModal() {
    this.selectedOrdre = null;
  }

  onProduitChange() {
    if (this.selectedProduitId) {
      this.currentOrdre.produit = {
        id: this.selectedProduitId, nom: ''
      };
    } else {
      this.currentOrdre.produit = undefined;
    }
  }

  onMachineChange() {
    if (this.selectedMachineId) {
      this.currentOrdre.machine = {
        id: this.selectedMachineId, nom: ''
      };
    } else {
      this.currentOrdre.machine = undefined;
    }
  }

  onTechnicienChange() {
    if (this.selectedTechnicienId) {
      this.currentOrdre.technicien = {
        id: this.selectedTechnicienId, nom: ''
      };
    } else {
      this.currentOrdre.technicien = undefined;
    }
  }

  changeStatut(id: number, statut: string) {
    this.api.changeStatutOrdre(id, statut).subscribe({
      next: () => {
        this.loadOrdres();
      },
      error: (err) => console.error('Erreur statut:', err)
    });
  }

  saveOrdre() {
    this.submitted = true;
    if (!this.selectedProduitId) return;
    if (!this.currentOrdre.quantite ||
        this.currentOrdre.quantite < 1) return;

    if (this.editMode && this.currentOrdre.id) {
      this.api.updateOrdre(
        this.currentOrdre.id,
        this.currentOrdre).subscribe({
          next: () => {
            this.cancelForm();
            this.loadOrdres();
            alert('Ordre modifié avec succès !');
          },
          error: (err) => {
            console.error('Erreur:', err);
            alert('Erreur lors de la modification !');
          }
        });
    } else {
      this.api.createOrdre(this.currentOrdre).subscribe({
        next: () => {
          this.cancelForm();
          this.loadOrdres();
          alert('Ordre créé avec succès !');
        },
        error: (err) => {
          console.error('Erreur:', err);
          alert('Erreur lors de la création !');
        }
      });
    }
  }

  deleteOrdre(id: number) {
    if (confirm('Voulez-vous supprimer cet ordre ?')) {
      this.api.deleteOrdre(id).subscribe({
        next: () => {
          this.loadOrdres();
          alert('Ordre supprimé !');
        },
        error: (err) => console.error('Erreur:', err)
      });
    }
  }

  cancelForm() {
    this.showForm = false;
    this.editMode = false;
    this.submitted = false;
    this.currentOrdre = { quantite: 0 };
    this.selectedProduitId = null;
    this.selectedMachineId = null;
    this.selectedTechnicienId = null;
  }
}