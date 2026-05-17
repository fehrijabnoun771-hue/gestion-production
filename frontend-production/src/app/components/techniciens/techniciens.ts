import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Api, Technicien, Machine } from '../../services/api';

@Component({
  selector: 'app-techniciens',
  imports: [CommonModule, FormsModule],
  templateUrl: './techniciens.html',
  styleUrl: './techniciens.scss'
})
export class Techniciens implements OnInit {

  techniciens: Technicien[] = [];
  filteredTechniciens: Technicien[] = [];
  machines: Machine[] = [];
  currentTechnicien: Technicien = { nom: '' };
  selectedTechnicien: Technicien | null = null;
  selectedMachineId: number | null = null;
  showForm = false;
  editMode = false;
  submitted = false;
  searchKeyword = '';
  filterAssigne = '';

  constructor(private api: Api) {}

  ngOnInit() {
    this.loadTechniciens();
    this.loadMachines();
  }

 loadTechniciens() {
  this.api.getTechniciens().subscribe({
    next: (data) => {
      this.techniciens = data;
      this.filteredTechniciens = [...data];
    },
    error: (err) => {
      console.error('Erreur chargement:', err);
    }
  });
}

  loadMachines() {
    this.api.getMachines().subscribe(data => {
      this.machines = data;
    });
  }

  filterTechniciens() {
    let filtered = this.techniciens;
    if (this.searchKeyword.trim()) {
      filtered = filtered.filter(t =>
        t.nom.toLowerCase().includes(
          this.searchKeyword.toLowerCase()));
    }
    if (this.filterAssigne === 'assigne') {
      filtered = filtered.filter(t => t.machineAssignee);
    } else if (this.filterAssigne === 'libre') {
      filtered = filtered.filter(t => !t.machineAssignee);
    }
    this.filteredTechniciens = filtered;
  }

  resetFilters() {
    this.searchKeyword = '';
    this.filterAssigne = '';
    this.filteredTechniciens = this.techniciens;
  }

  countAssignes(): number {
    return this.techniciens.filter(t => t.machineAssignee).length;
  }

  countNonAssignes(): number {
    return this.techniciens.filter(t => !t.machineAssignee).length;
  }

  getCompetences(competences: string): string[] {
    return competences.split(',').map(c => c.trim());
  }

  emailInvalid(): boolean {
    if (!this.currentTechnicien.email) return false;
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return !re.test(this.currentTechnicien.email);
  }

  openForm() {
    this.showForm = true;
    this.editMode = false;
    this.submitted = false;
    this.currentTechnicien = { nom: '' };
    this.selectedMachineId = null;
  }

  editTechnicien(t: Technicien) {
    this.currentTechnicien = { ...t };
    this.selectedMachineId = t.machineAssignee?.id || null;
    this.showForm = true;
    this.editMode = true;
    this.submitted = false;
  }

  viewTechnicien(t: Technicien) {
    this.selectedTechnicien = t;
  }

  closeModal() {
    this.selectedTechnicien = null;
  }

  onMachineChange() {
    if (this.selectedMachineId) {
      this.currentTechnicien.machineAssignee = {
        id: this.selectedMachineId, nom: ''
      };
    } else {
      this.currentTechnicien.machineAssignee = undefined;
    }
  }

  saveTechnicien() {
  this.submitted = true;
  if (!this.currentTechnicien.nom?.trim()) return;
  if (this.emailInvalid()) return;

  if (this.editMode && this.currentTechnicien.id) {
    this.api.updateTechnicien(
      this.currentTechnicien.id,
      this.currentTechnicien).subscribe({
        next: () => {
          this.cancelForm();
          this.loadTechniciens();
          alert('Technicien modifié avec succès !');
        },
        error: (err) => {
          console.error('Erreur:', err);
          alert('Erreur lors de la modification !');
        }
      });
  } else {
    this.api.createTechnicien(this.currentTechnicien)
      .subscribe({
        next: () => {
          this.cancelForm();
          this.loadTechniciens();
          alert('Technicien créé avec succès !');
        },
        error: (err) => {
          console.error('Erreur:', err);
          alert('Erreur lors de la création !');
        }
      });
  }
}

  deleteTechnicien(id: number) {
    if (confirm('Voulez-vous supprimer ce technicien ?')) {
      this.api.deleteTechnicien(id).subscribe(() => {
        this.loadTechniciens();
        alert('Technicien supprimé !');
      });
    }
  }

  cancelForm() {
    this.showForm = false;
    this.editMode = false;
    this.submitted = false;
    this.currentTechnicien = { nom: '' };
    this.selectedMachineId = null;
  }
}