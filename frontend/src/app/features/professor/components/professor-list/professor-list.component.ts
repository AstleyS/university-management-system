import {Component, OnInit} from '@angular/core';
import {Professor} from '../../models/professor';
import {ProfessorService} from '../../services/professor.service';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-professor-list',
  standalone: true,
  imports: [RouterLink],
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

  onDeleteProfessor(id: number) {
    this.professorService.deleteProfessor(id);
  }

}
