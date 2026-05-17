import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Api, Maintenance, Machine, Technicien } from '../../services/api';

@Component({
  selector: 'app-maintenances',
  imports: [CommonModule, FormsModule],
  templateUrl: './maintenances.html',
  styleUrl: './maintenances.scss'
})
export class Maintenances implements OnInit {

  maintenances: Maintenance[] = [];
  filteredMaintenances: Maintenance[] = [];
  machines: Machine[] = [];
  techniciens: Technicien[] = [];
  currentMaintenance: Maintenance = { dateMaintenance: '' };
  selectedMaintenance: Maintenance | null = null;
  selectedMachineId: number | null = null;
  selectedTechnicienId: number | null = null;
  showForm = false;
  editMode = false;
  submitted = false;
  searchKeyword = '';
  filterStatut = '';
  filterType = '';

  constructor(private api: Api) {}

  ngOnInit() {
    this.loadMaintenances();
    this.loadMachines();
    this.loadTechniciens();
  }

  loadMaintenances() {
    this.api.getMaintenances().subscribe({
      next: (data) => {
        this.maintenances = data;
        this.filteredMaintenances = [...data];
      },
      error: (err) => console.error('Erreur:', err)
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

  filterMaintenances() {
    let filtered = this.maintenances;

    if (this.searchKeyword.trim()) {
      filtered = filtered.filter(m =>
        m.machine?.nom?.toLowerCase().includes(
          this.searchKeyword.toLowerCase()));
    }

    if (this.filterStatut) {
      filtered = filtered.filter(
        m => m.statut === this.filterStatut);
    }

    if (this.filterType) {
      filtered = filtered.filter(
        m => m.type === this.filterType);
    }

    this.filteredMaintenances = filtered;
  }

  resetFilters() {
    this.searchKeyword = '';
    this.filterStatut = '';
    this.filterType = '';
    this.filteredMaintenances = [...this.maintenances];
  }

  countByStatut(statut: string): number {
    return this.maintenances.filter(
      m => m.statut === statut).length;
  }

  changeStatut(id: number, statut: string) {
    this.api.updateMaintenance(id, {
      ...this.maintenances.find(m => m.id === id)!,
      statut: statut
    }).subscribe({
      next: () => this.loadMaintenances(),
      error: (err) => console.error('Erreur:', err)
    });
  }

  openForm() {
    this.showForm = true;
    this.editMode = false;
    this.submitted = false;
    this.currentMaintenance = {
      dateMaintenance: '',
      type: 'PREVENTIVE',
      statut: 'PLANIFIEE'
    };
    this.selectedMachineId = null;
    this.selectedTechnicienId = null;
  }

  editMaintenance(m: Maintenance) {
    this.currentMaintenance = { ...m };
    this.selectedMachineId = m.machine?.id || null;
    this.selectedTechnicienId = m.technicien?.id || null;
    this.showForm = true;
    this.editMode = true;
    this.submitted = false;
  }

  viewMaintenance(m: Maintenance) {
    this.selectedMaintenance = m;
  }

  closeModal() {
    this.selectedMaintenance = null;
  }

  onMachineChange() {
    if (this.selectedMachineId) {
      this.currentMaintenance.machine = {
        id: this.selectedMachineId, nom: ''
      };
    } else {
      this.currentMaintenance.machine = undefined;
    }
  }

  onTechnicienChange() {
    if (this.selectedTechnicienId) {
      this.currentMaintenance.technicien = {
        id: this.selectedTechnicienId, nom: ''
      };
    } else {
      this.currentMaintenance.technicien = undefined;
    }
  }

  saveMaintenance() {
    this.submitted = true;
    if (!this.selectedMachineId) return;
    if (!this.selectedTechnicienId) return;
    if (!this.currentMaintenance.dateMaintenance) return;

    if (this.editMode && this.currentMaintenance.id) {
      this.api.updateMaintenance(
        this.currentMaintenance.id,
        this.currentMaintenance).subscribe({
          next: () => {
            this.cancelForm();
            this.loadMaintenances();
            alert('Maintenance modifiée avec succès !');
          },
          error: (err) => {
            console.error('Erreur:', err);
            alert('Erreur lors de la modification !');
          }
        });
    } else {
      this.api.createMaintenance(
        this.currentMaintenance).subscribe({
          next: () => {
            this.cancelForm();
            this.loadMaintenances();
            alert('Maintenance créée avec succès !');
          },
          error: (err) => {
            console.error('Erreur:', err);
            alert('Erreur lors de la création !');
          }
        });
    }
  }

  deleteMaintenance(id: number) {
    if (confirm('Voulez-vous supprimer cette maintenance ?')) {
      this.api.deleteMaintenance(id).subscribe({
        next: () => {
          this.loadMaintenances();
          alert('Maintenance supprimée !');
        },
        error: (err) => console.error('Erreur:', err)
      });
    }
  }

  cancelForm() {
    this.showForm = false;
    this.editMode = false;
    this.submitted = false;
    this.currentMaintenance = { dateMaintenance: '' };
    this.selectedMachineId = null;
    this.selectedTechnicienId = null;
  }
}