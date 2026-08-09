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

  faculties?: Faculty[]

  ngOnInit() {

    this.facultyService.getFaculties().subscribe({

      next: (data) => {
        console.log(data);
        this.faculties = data;
      },

      error: err => console.error(err)

    });

  }


}
