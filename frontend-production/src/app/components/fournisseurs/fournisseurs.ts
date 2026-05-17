import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Api, Fournisseur } from '../../services/api';

@Component({
  selector: 'app-fournisseurs',
  imports: [CommonModule, FormsModule],
  templateUrl: './fournisseurs.html',
  styleUrl: './fournisseurs.scss'
})
export class Fournisseurs implements OnInit {

  fournisseurs: Fournisseur[] = [];
  filteredFournisseurs: Fournisseur[] = [];
  currentFournisseur: Fournisseur = { nom: '', note: 3 };
  selectedFournisseur: Fournisseur | null = null;
  showForm = false;
  editMode = false;
  submitted = false;
  searchKeyword = '';
  filterNote = '';

  constructor(private api: Api) {}

  ngOnInit() {
    this.loadFournisseurs();
  }

  loadFournisseurs() {
    this.api.getFournisseurs().subscribe({
      next: (data) => {
        this.fournisseurs = data;
        this.filteredFournisseurs = [...data];
      },
      error: (err) => console.error('Erreur:', err)
    });
  }

  filterFournisseurs() {
    let filtered = this.fournisseurs;

    if (this.searchKeyword.trim()) {
      filtered = filtered.filter(f =>
        f.nom.toLowerCase().includes(
          this.searchKeyword.toLowerCase()));
    }

    if (this.filterNote) {
      const minNote = parseInt(this.filterNote);
      filtered = filtered.filter(
        f => (f.note || 0) >= minNote);
    }

    this.filteredFournisseurs = filtered;
  }

  resetFilters() {
    this.searchKeyword = '';
    this.filterNote = '';
    this.filteredFournisseurs = [...this.fournisseurs];
  }

  countByNote(min: number, max: number): number {
    return this.fournisseurs.filter(
      f => (f.note || 0) >= min &&
           (f.note || 0) <= max).length;
  }

  getStars(note: number): string[] {
    const stars = [];
    for (let i = 1; i <= 5; i++) {
      stars.push(i <= note ? 'full' : 'empty');
    }
    return stars;
  }

  emailInvalid(): boolean {
    if (!this.currentFournisseur.email) return false;
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return !re.test(this.currentFournisseur.email);
  }

  openForm() {
    this.showForm = true;
    this.editMode = false;
    this.submitted = false;
    this.currentFournisseur = { nom: '', note: 3 };
  }

  editFournisseur(f: Fournisseur) {
    this.currentFournisseur = { ...f };
    this.showForm = true;
    this.editMode = true;
    this.submitted = false;
  }

  viewFournisseur(f: Fournisseur) {
    this.selectedFournisseur = f;
  }

  closeModal() {
    this.selectedFournisseur = null;
  }

  saveFournisseur() {
    this.submitted = true;
    if (!this.currentFournisseur.nom?.trim()) return;
    if (this.emailInvalid()) return;

    if (this.editMode && this.currentFournisseur.id) {
      this.api.updateFournisseur(
        this.currentFournisseur.id,
        this.currentFournisseur).subscribe({
          next: () => {
            this.cancelForm();
            this.loadFournisseurs();
            alert('Fournisseur modifié avec succès !');
          },
          error: (err) => {
            console.error('Erreur:', err);
            alert('Erreur lors de la modification !');
          }
        });
    } else {
      this.api.createFournisseur(
        this.currentFournisseur).subscribe({
          next: () => {
            this.cancelForm();
            this.loadFournisseurs();
            alert('Fournisseur créé avec succès !');
          },
          error: (err) => {
            console.error('Erreur:', err);
            alert('Erreur lors de la création !');
          }
        });
    }
  }

  deleteFournisseur(id: number) {
    if (confirm('Voulez-vous supprimer ce fournisseur ?')) {
      this.api.deleteFournisseur(id).subscribe({
        next: () => {
          this.loadFournisseurs();
          alert('Fournisseur supprimé !');
        },
        error: (err) => console.error('Erreur:', err)
      });
    }
  }

  cancelForm() {
    this.showForm = false;
    this.editMode = false;
    this.submitted = false;
    this.currentFournisseur = { nom: '', note: 3 };
  }
}