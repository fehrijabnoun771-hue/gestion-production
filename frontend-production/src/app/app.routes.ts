import { Routes } from '@angular/router';
import { Dashboard } from './components/dashboard/dashboard';
import { Machines } from './components/machines/machines';
import { Techniciens } from './components/techniciens/techniciens';
import { Produits } from './components/produits/produits';
import { Maintenances } from './components/maintenances/maintenances';
import { Ordres } from './components/ordres/ordres';
import { Alertes } from './components/alertes/alertes';
import { Fournisseurs } from './components/fournisseurs/fournisseurs';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: Dashboard },
  { path: 'machines', component: Machines },
  { path: 'techniciens', component: Techniciens },
  { path: 'produits', component: Produits },
  { path: 'maintenances', component: Maintenances },
  { path: 'ordres', component: Ordres },
  { path: 'alertes', component: Alertes },
  { path: 'fournisseurs', component: Fournisseurs }
];