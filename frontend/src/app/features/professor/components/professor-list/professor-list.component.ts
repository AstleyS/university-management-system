import {Component, OnInit} from '@angular/core';
import {Professor} from '../../models/professor';
import {ProfessorService} from '../../services/professor.service';

@Component({
  selector: 'app-professor-list',
  standalone: true,
  templateUrl: './professor-list.component.html',
  styleUrl: './professor-list.component.scss'
})
export class ProfessorListComponent implements OnInit {

  professors?: Professor[]

  constructor(private professorService: ProfessorService) {}

  ngOnInit() {

    this.professorService.getProfessors().subscribe({
      next: (data) => {
        this.professors = data
        console.log(data)
      },
      error: (error) => console.error(error)
    })
  }

}
