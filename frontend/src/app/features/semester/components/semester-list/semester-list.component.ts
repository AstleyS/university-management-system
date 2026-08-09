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

  semesters?: Semester[]

  ngOnInit() {

    this.semesterService.getSemesters().subscribe({

      next: (data) => {
        console.log(data)
        this.semesters = data;
      },

      error: err => console.error(err)

    });
  }


}
