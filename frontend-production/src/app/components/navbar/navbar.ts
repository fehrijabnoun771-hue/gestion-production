import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Api } from '../../services/api';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss'
})
export class Navbar implements OnInit {

  alertesCount = 0;

  constructor(private api: Api) {}

  ngOnInit() {
    this.api.countAlertesNonLues().subscribe(count => {
      this.alertesCount = count;
    });
  }
}