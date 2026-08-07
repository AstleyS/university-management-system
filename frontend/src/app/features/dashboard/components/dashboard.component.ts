import {Component, OnInit} from '@angular/core';
import {DashboardService} from '../services/dashboard.service';
import {AcademicStats} from '../models/dashboard';
import {AuthService} from '../../auth/services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  username='';
  stats?: AcademicStats;

  constructor(
    private dashboardService: DashboardService,
    private authService: AuthService,
    private router: Router
    ) {
  }

  ngOnInit() {

    this.dashboardService.getAcademicStats().subscribe({
      next: (data) => {
        this.stats = data
        console.log(data)
      },
      error: (error) => console.error(error)
    })

    this.username = this.authService.getCurrentUser()?.username ?? '';
    console.log("Current user: " + this.username)

  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/'])
  }

}
