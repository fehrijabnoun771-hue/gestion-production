import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  Api, Produit, Fournisseur, CategorieProduit
} from '../../services/api';

@Component({
  selector: 'app-produits',
  imports: [CommonModule, FormsModule],
  templateUrl: './produits.html',
  styleUrl: './produits.scss'
})
export class Produits implements OnInit {

  produits: Produit[] = [];
  filteredProduits: Produit[] = [];
  fournisseurs: Fournisseur[] = [];
  categories: CategorieProduit[] = [];
  currentProduit: Produit = { nom: '' };
  selectedProduit: Produit | null = null;  // ← cette ligne
  selectedFournisseurId: number | null = null;
  selectedCategorieId: number | null = null;
  showForm = false;
  editMode = false;
  submitted = false;
  searchKeyword = '';
  filterType = '';
  filterStock = '';
  constructor(private api: Api) {}

  ngOnInit() {
    this.loadProduits();
    this.loadFournisseurs();
    this.loadCategories();
  }

  loadProduits() {
    this.api.getProduits().subscribe({
      next: (data) => {
        this.produits = data;
        this.filteredProduits = [...data];
      },
      error: (err) => console.error('Erreur:', err)
    });
  }

  loadFournisseurs() {
    this.api.getFournisseurs().subscribe(data => {
      this.fournisseurs = data;
    });
  }

  loadCategories() {
    this.api.getCategories().subscribe(data => {
      this.categories = data;
    });
  }

  filterProduits() {
    let filtered = this.produits;

    if (this.searchKeyword.trim()) {
      filtered = filtered.filter(p =>
        p.nom.toLowerCase().includes(
          this.searchKeyword.toLowerCase()));
    }

    if (this.filterType) {
      filtered = filtered.filter(p => p.type === this.filterType);
    }

    if (this.filterStock === 'ok') {
      filtered = filtered.filter(p => p.stock! > 10);
    } else if (this.filterStock === 'faible') {
      filtered = filtered.filter(
        p => p.stock! > 0 && p.stock! <= 10);
    } else if (this.filterStock === 'epuise') {
      filtered = filtered.filter(p => p.stock === 0);
    }

    this.filteredProduits = filtered;
  }

  resetFilters() {
    this.searchKeyword = '';
    this.filterType = '';
    this.filterStock = '';
    this.filteredProduits = [...this.produits];
  }

  countStockOk(): number {
    return this.produits.filter(p => p.stock! > 10).length;
  }

  countStockFaible(): number {
    return this.produits.filter(
      p => p.stock! > 0 && p.stock! <= 10).length;
  }

  countStockEpuise(): number {
    return this.produits.filter(p => p.stock === 0).length;
  }

  openForm() {
    this.showForm = true;
    this.editMode = false;
    this.submitted = false;
    this.currentProduit = { nom: '', stock: 0 };
    this.selectedFournisseurId = null;
    this.selectedCategorieId = null;
  }

  editProduit(p: Produit) {
    this.currentProduit = { ...p };
    this.selectedFournisseurId = p.fournisseur?.id || null;
    this.selectedCategorieId = p.categorie?.id || null;
    this.showForm = true;
    this.editMode = true;
    this.submitted = false;
  }

  viewProduit(p: Produit) {
    this.selectedProduit = p;
  }

  closeModal() {
    this.selectedProduit = null;
  }

  onFournisseurChange() {
    if (this.selectedFournisseurId) {
      this.currentProduit.fournisseur = {
        id: this.selectedFournisseurId, nom: ''
      };
    } else {
      this.currentProduit.fournisseur = undefined;
    }
  }

  onCategorieChange() {
    if (this.selectedCategorieId) {
      this.currentProduit.categorie = {
        id: this.selectedCategorieId, nom: ''
      };
    } else {
      this.currentProduit.categorie = undefined;
    }
  }

  saveProduit() {
    this.submitted = true;
    if (!this.currentProduit.nom?.trim()) return;
    if (this.currentProduit.stock! < 0) return;

    if (this.editMode && this.currentProduit.id) {
      this.api.updateProduit(
        this.currentProduit.id,
        this.currentProduit).subscribe({
          next: () => {
            this.cancelForm();
            this.loadProduits();
            alert('Produit modifié avec succès !');
          },
          error: (err) => {
            console.error('Erreur:', err);
            alert('Erreur lors de la modification !');
          }
        });
    } else {
      this.api.createProduit(this.currentProduit).subscribe({
        next: () => {
          this.cancelForm();
          this.loadProduits();
          alert('Produit créé avec succès !');
        },
        error: (err) => {
          console.error('Erreur:', err);
          alert('Erreur lors de la création !');
        }
      });
    }
  }

  deleteProduit(id: number) {
    if (confirm('Voulez-vous supprimer ce produit ?')) {
      this.api.deleteProduit(id).subscribe({
        next: () => {
          this.loadProduits();
          alert('Produit supprimé !');
        },
        error: (err) => console.error('Erreur:', err)
      });
    }
  }

  cancelForm() {
    this.showForm = false;
    this.editMode = false;
    this.submitted = false;
    this.currentProduit = { nom: '' };
    this.selectedFournisseurId = null;
    this.selectedCategorieId = null;
  }
}