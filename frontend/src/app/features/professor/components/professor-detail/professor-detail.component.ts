import {Component, OnInit} from '@angular/core';
import {Professor} from '../../models/professor';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {ProfessorService} from '../../services/professor.service';

@Component({
  selector: 'app-professor-detail',
  imports: [RouterLink],
  templateUrl: './professor-detail.component.html',
  styleUrl: './professor-detail.component.scss'
})
export class ProfessorDetailComponent implements OnInit {

  professor?: Professor

  loading = true;
  errorMessage = ''

  constructor(
    private route: ActivatedRoute,
    private professorService: ProfessorService
  ) {}

  ngOnInit() {

    const id = Number(this.route.snapshot.paramMap.get('id'))

    if (!id) {
      this.errorMessage = 'Invalid professor ID'
      return;
    }

    this.professorService.getProfessorById(id).subscribe({

      next: (data) => {
        console.log(`Professor with id ${id}: `, data)
        this.loading = false;
        this.professor = data;
      },

      error: (error) => {
        console.error(error)
        this.errorMessage = 'Failed to load Professor';
        this.loading = false;
      }

    })
  }
}
