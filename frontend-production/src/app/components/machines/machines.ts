import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Api, Machine } from '../../services/api';

@Component({
  selector: 'app-machines',
  imports: [CommonModule, FormsModule],
  templateUrl: './machines.html',
  styleUrl: './machines.scss'
})
export class Machines implements OnInit {

  machines: Machine[] = [];
  filteredMachines: Machine[] = [];
  currentMachine: Machine = { nom: '' };
  selectedMachine: Machine | null = null;
  showForm = false;
  editMode = false;
  submitted = false;
  searchKeyword = '';
  filterEtat = '';
  maintenanceFilter = false;

  constructor(private api: Api, private router: Router) {}

  ngOnInit() {
    this.loadMachines();
  }

  loadMachines() {
    this.api.getMachines().subscribe(data => {
      this.machines = data;
      this.filteredMachines = data;
      this.maintenanceFilter = false;
    });
  }

  searchMachines() {
    if (this.searchKeyword.trim()) {
      this.filteredMachines = this.machines.filter(m =>
        m.nom.toLowerCase().includes(
          this.searchKeyword.toLowerCase()));
    } else {
      this.filteredMachines = this.machines;
    }
  }

  filterByEtat() {
    if (this.filterEtat) {
      this.api.getMachinesByEtat(this.filterEtat).subscribe(data => {
        this.filteredMachines = data;
      });
    } else {
      this.filteredMachines = this.machines;
    }
  }

  getMachinesNeedingMaintenance() {
    this.api.getMachines().subscribe(data => {
      const today = new Date();
      this.filteredMachines = data.filter(m => {
        if (!m.maintenanceProchaine) return false;
        const date = new Date(m.maintenanceProchaine);
        const diff = (date.getTime() - today.getTime())
          / (1000 * 60 * 60 * 24);
        return diff <= 30;
      });
      this.maintenanceFilter = true;
    });
  }

  resetFilters() {
    this.searchKeyword = '';
    this.filterEtat = '';
    this.maintenanceFilter = false;
    this.filteredMachines = this.machines;
  }

  countByEtat(etat: string): number {
    return this.machines.filter(m => m.etat === etat).length;
  }

  isMaintenanceSoon(machine: Machine): boolean {
    if (!machine.maintenanceProchaine) return false;
    const today = new Date();
    const date = new Date(machine.maintenanceProchaine);
    const diff = (date.getTime() - today.getTime())
      / (1000 * 60 * 60 * 24);
    return diff <= 30 && diff >= 0;
  }

  openForm() {
    this.showForm = true;
    this.editMode = false;
    this.submitted = false;
    this.currentMachine = { nom: '', etat: 'DISPONIBLE' };
  }

  editMachine(machine: Machine) {
    this.currentMachine = { ...machine };
    this.showForm = true;
    this.editMode = true;
    this.submitted = false;
  }

  viewMachine(machine: Machine) {
    this.selectedMachine = machine;
  }

  closeModal() {
    this.selectedMachine = null;
  }

  saveMachine() {
    this.submitted = true;
    if (!this.currentMachine.nom?.trim()) return;

    if (this.editMode && this.currentMachine.id) {
      this.api.updateMachine(
        this.currentMachine.id,
        this.currentMachine).subscribe(() => {
          this.loadMachines();
          this.cancelForm();
          alert('Machine modifiée avec succès !');
        });
    } else {
      this.api.createMachine(this.currentMachine)
        .subscribe(() => {
          this.loadMachines();
          this.cancelForm();
          alert('Machine créée avec succès !');
        });
    }
  }

  deleteMachine(id: number) {
    if (confirm('Voulez-vous vraiment supprimer cette machine ?')) {
      this.api.deleteMachine(id).subscribe(() => {
        this.loadMachines();
        alert('Machine supprimée avec succès !');
      });
    }
  }

  planifierMaintenance(machine: Machine) {
    if (confirm(
      'Créer une maintenance planifiée pour ' + machine.nom + ' ?')) {
      this.api.getTechniciens().subscribe(techniciens => {
        if (techniciens.length === 0) {
          alert('Aucun technicien ! Créez un technicien d\'abord.');
          return;
        }
        const maintenance = {
          machine: { id: machine.id, nom: machine.nom },
          technicien: { id: techniciens[0].id, nom: techniciens[0].nom },
          dateMaintenance: machine.maintenanceProchaine!,
          type: 'PREVENTIVE',
          statut: 'PLANIFIEE',
          description: 'Maintenance préventive pour ' + machine.nom
        };
        this.api.createMaintenance(maintenance).subscribe({
          next: () => {
            alert('Maintenance planifiée avec succès !');
            this.router.navigate(['/maintenances']);
          },
          error: (err) => {
            console.error('Erreur:', err);
            alert('Erreur lors de la création !');
          }
        });
      });
    }
  }

  cancelForm() {
    this.showForm = false;
    this.editMode = false;
    this.submitted = false;
    this.currentMachine = { nom: '' };
  }
}