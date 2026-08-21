import {Component, OnInit} from '@angular/core';
import {Semester} from '../../models/semester';
import {SemesterService} from '../../services/semester.service';

@Component({
  selector: 'app-semester-list',
  standalone: true,
  templateUrl: './semester-list.component.html',
  styleUrl: './semester-list.component.scss'
})
export class SemesterListComponent implements OnInit {

  constructor(private semesterService: SemesterService) {}

  semesters: Semester[] = []
  loading = true
  errorMessage = ''

  ngOnInit() {

    this.semesterService.getSemesters().subscribe({

      next: (data) => {
        console.log(data)
        this.semesters = data;
        this.loading = false
      },

      error: err => {
        console.error(err)
        this.loading = false
        this.errorMessage = 'Error loading semesters'
      }

    });
  }


}
