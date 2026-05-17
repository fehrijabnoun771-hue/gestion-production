import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Machine {
  id?: number;
  nom: string;
  etat?: string;
  maintenanceProchaine?: string;
  dateAcquisition?: string;
  description?: string;
}

export interface Technicien {
  id?: number;
  nom: string;
  competences?: string;
  telephone?: string;
  email?: string;
  machineAssignee?: Machine;
}

export interface Fournisseur {
  id?: number;
  nom: string;
  contact?: string;
  telephone?: string;
  email?: string;
  adresse?: string;
  note?: number;
}

export interface CategorieProduit {
  id?: number;
  nom: string;
  description?: string;
}

export interface Produit {
  id?: number;
  nom: string;
  type?: string;
  stock?: number;
  prix?: number;
  fournisseur?: Fournisseur;
  categorie?: CategorieProduit;
}

export interface Maintenance {
  id?: number;
  machine?: Machine;
  technicien?: Technicien;
  dateMaintenance: string;
  type?: string;
  description?: string;
  cout?: number;
  statut?: string;
}

export interface OrdreFabrication {
  id?: number;
  produit?: Produit;
  quantite: number;
  dateCreation?: string;
  dateFin?: string;
  machine?: Machine;
  technicien?: Technicien;
  statut?: string;
  priorite?: string;
}

export interface Alerte {
  id?: number;
  type: string;
  message: string;
  dateAlerte?: string;
  statut?: string;
  machine?: Machine;
  produit?: Produit;
}

export interface Utilisateur {
  id?: number;
  nom: string;
  email: string;
  motDePasse?: string;
  role: string;
  actif?: boolean;
}

export interface PieceRechange {
  id?: number;
  nom: string;
  reference: string;
  stock?: number;
  prix?: number;
  fournisseur?: Fournisseur;
}

@Injectable({
  providedIn: 'root'
})
export class Api {

  private baseUrl = window.location.hostname === 'localhost'
  ? 'http://localhost:8080/api'
  : '/api';

  constructor(private http: HttpClient) {}

  // ===== MACHINES =====
  getMachines(): Observable<Machine[]> {
    return this.http.get<Machine[]>(`${this.baseUrl}/machines`);
  }
  getMachine(id: number): Observable<Machine> {
    return this.http.get<Machine>(`${this.baseUrl}/machines/${id}`);
  }
  createMachine(machine: Machine): Observable<Machine> {
    return this.http.post<Machine>(`${this.baseUrl}/machines`, machine);
  }
  updateMachine(id: number, machine: Machine): Observable<Machine> {
    return this.http.put<Machine>(`${this.baseUrl}/machines/${id}`, machine);
  }
  deleteMachine(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/machines/${id}`);
  }
  getMachinesByEtat(etat: string): Observable<Machine[]> {
    return this.http.get<Machine[]>(`${this.baseUrl}/machines/etat/${etat}`);
  }

  // ===== TECHNICIENS =====
  getTechniciens(): Observable<Technicien[]> {
    return this.http.get<Technicien[]>(`${this.baseUrl}/techniciens`);
  }
  getTechnicien(id: number): Observable<Technicien> {
    return this.http.get<Technicien>(`${this.baseUrl}/techniciens/${id}`);
  }
  createTechnicien(t: Technicien): Observable<Technicien> {
    return this.http.post<Technicien>(`${this.baseUrl}/techniciens`, t);
  }
  updateTechnicien(id: number, t: Technicien): Observable<Technicien> {
    return this.http.put<Technicien>(`${this.baseUrl}/techniciens/${id}`, t);
  }
  deleteTechnicien(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/techniciens/${id}`);
  }

  // ===== PRODUITS =====
  getProduits(): Observable<Produit[]> {
    return this.http.get<Produit[]>(`${this.baseUrl}/produits`);
  }
  getProduit(id: number): Observable<Produit> {
    return this.http.get<Produit>(`${this.baseUrl}/produits/${id}`);
  }
  createProduit(p: Produit): Observable<Produit> {
    return this.http.post<Produit>(`${this.baseUrl}/produits`, p);
  }
  updateProduit(id: number, p: Produit): Observable<Produit> {
    return this.http.put<Produit>(`${this.baseUrl}/produits/${id}`, p);
  }
  deleteProduit(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/produits/${id}`);
  }

  // ===== MAINTENANCES =====
  getMaintenances(): Observable<Maintenance[]> {
    return this.http.get<Maintenance[]>(`${this.baseUrl}/maintenances`);
  }
  getMaintenance(id: number): Observable<Maintenance> {
    return this.http.get<Maintenance>(`${this.baseUrl}/maintenances/${id}`);
  }
  createMaintenance(m: Maintenance): Observable<Maintenance> {
    return this.http.post<Maintenance>(`${this.baseUrl}/maintenances`, m);
  }
  updateMaintenance(id: number, m: Maintenance): Observable<Maintenance> {
    return this.http.put<Maintenance>(`${this.baseUrl}/maintenances/${id}`, m);
  }
  deleteMaintenance(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/maintenances/${id}`);
  }

  // ===== ORDRES FABRICATION =====
  getOrdres(): Observable<OrdreFabrication[]> {
    return this.http.get<OrdreFabrication[]>(`${this.baseUrl}/ordres`);
  }
  getOrdre(id: number): Observable<OrdreFabrication> {
    return this.http.get<OrdreFabrication>(`${this.baseUrl}/ordres/${id}`);
  }
  createOrdre(o: OrdreFabrication): Observable<OrdreFabrication> {
    return this.http.post<OrdreFabrication>(`${this.baseUrl}/ordres`, o);
  }
  updateOrdre(id: number, o: OrdreFabrication): Observable<OrdreFabrication> {
    return this.http.put<OrdreFabrication>(`${this.baseUrl}/ordres/${id}`, o);
  }
  deleteOrdre(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/ordres/${id}`);
  }
  changeStatutOrdre(id: number, statut: string): Observable<OrdreFabrication> {
    return this.http.put<OrdreFabrication>(
      `${this.baseUrl}/ordres/${id}/statut?statut=${statut}`, {});
  }

  // ===== ALERTES =====
  getAlertes(): Observable<Alerte[]> {
    return this.http.get<Alerte[]>(`${this.baseUrl}/alertes`);
  }
  getAlertesNonLues(): Observable<Alerte[]> {
    return this.http.get<Alerte[]>(`${this.baseUrl}/alertes/non-lues`);
  }
  countAlertesNonLues(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/alertes/count`);
  }
  marquerAlerteLue(id: number): Observable<Alerte> {
    return this.http.put<Alerte>(`${this.baseUrl}/alertes/${id}/lire`, {});
  }
  createAlerte(a: Alerte): Observable<Alerte> {
    return this.http.post<Alerte>(`${this.baseUrl}/alertes`, a);
  }
  deleteAlerte(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/alertes/${id}`);
  }

  // ===== FOURNISSEURS =====
  getFournisseurs(): Observable<Fournisseur[]> {
    return this.http.get<Fournisseur[]>(`${this.baseUrl}/fournisseurs`);
  }
  createFournisseur(f: Fournisseur): Observable<Fournisseur> {
    return this.http.post<Fournisseur>(`${this.baseUrl}/fournisseurs`, f);
  }
  updateFournisseur(id: number, f: Fournisseur): Observable<Fournisseur> {
    return this.http.put<Fournisseur>(`${this.baseUrl}/fournisseurs/${id}`, f);
  }
  deleteFournisseur(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/fournisseurs/${id}`);
  }

  // ===== CATEGORIES =====
  getCategories(): Observable<CategorieProduit[]> {
    return this.http.get<CategorieProduit[]>(`${this.baseUrl}/categories`);
  }
  createCategorie(c: CategorieProduit): Observable<CategorieProduit> {
    return this.http.post<CategorieProduit>(`${this.baseUrl}/categories`, c);
  }
  updateCategorie(id: number, c: CategorieProduit): Observable<CategorieProduit> {
    return this.http.put<CategorieProduit>(`${this.baseUrl}/categories/${id}`, c);
  }
  deleteCategorie(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/categories/${id}`);
  }

  // ===== UTILISATEURS =====
  getUtilisateurs(): Observable<Utilisateur[]> {
    return this.http.get<Utilisateur[]>(`${this.baseUrl}/utilisateurs`);
  }
  createUtilisateur(u: Utilisateur): Observable<Utilisateur> {
    return this.http.post<Utilisateur>(`${this.baseUrl}/utilisateurs`, u);
  }
  updateUtilisateur(id: number, u: Utilisateur): Observable<Utilisateur> {
    return this.http.put<Utilisateur>(`${this.baseUrl}/utilisateurs/${id}`, u);
  }
  deleteUtilisateur(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/utilisateurs/${id}`);
  }

  // ===== PIECES RECHANGE =====
  getPieces(): Observable<PieceRechange[]> {
    return this.http.get<PieceRechange[]>(`${this.baseUrl}/pieces`);
  }
  createPiece(p: PieceRechange): Observable<PieceRechange> {
    return this.http.post<PieceRechange>(`${this.baseUrl}/pieces`, p);
  }
  updatePiece(id: number, p: PieceRechange): Observable<PieceRechange> {
    return this.http.put<PieceRechange>(`${this.baseUrl}/pieces/${id}`, p);
  }
  deletePiece(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/pieces/${id}`);
  }


  updateAlerte(id: number, a: Alerte): Observable<Alerte> {
  return this.http.put<Alerte>(`${this.baseUrl}/alertes/${id}`, a);
}



}