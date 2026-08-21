import {Component, OnInit} from '@angular/core';
import {FacultyService} from '../../services/faculty.service';
import {Faculty} from '../../models/faculty';

@Component({
  selector: 'app-faculty-list',
  standalone: true,
  templateUrl: './faculty-list.component.html',
  styleUrl: './faculty-list.component.scss'
})
export class FacultyListComponent implements OnInit {

  constructor(private facultyService: FacultyService ) {}

  faculties: Faculty[] = []

  loading = true
  errorMessage = ''

  ngOnInit() {

    this.facultyService.getFaculties().subscribe({

      next: (data) => {
        console.log(data);
        this.faculties = data;
        this.loading = false
      },

      error: err => {
        console.error(err)
        this.loading = false;
        this.errorMessage = 'Error loading faculties'
      }

    });
  }
}
