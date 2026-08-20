import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { Professor } from '../../models/professor';
import { ProfessorService } from '../../services/professor.service';

@Component({
  selector: 'app-professor-list',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './professor-list.component.html',
  styleUrl: './professor-list.component.scss'
})
export class ProfessorListComponent implements OnInit {

  professors: Professor[] = [];

  searchTerm = '';

  loading = true;
  errorMessage = '';

  constructor(
    private professorService: ProfessorService
  ) {}

  ngOnInit() {

    this.loadProfessors();

  }

  loadProfessors() {

    this.professorService
      .getProfessors()
      .subscribe({

        next: (professors) => {

          this.professors = professors;

          this.loading = false;

        },

        error: (error) => {

          console.error(error);

          this.errorMessage =
            'Failed to load professors';

          this.loading = false;

        }

      });

  }

  get filteredProfessors(): Professor[] {

    const search =
      this.searchTerm
        .trim()
        .toLowerCase();

    if (!search) {
      return this.professors;
    }

    return this.professors.filter(professor => {

      const fullName =
        `${professor.firstName} ${professor.lastName}`
          .toLowerCase();

      return (
        fullName.includes(search) ||
        professor.email
          .toLowerCase()
          .includes(search)
      );

    });

  }

  onDeleteProfessor(id: number) {

    const professor =
      this.professors.find(
        professor => professor.id === id
      );

    if (!professor) {
      return;
    }

    const confirmed =
      confirm(
        `Delete professor "${professor.firstName} ${professor.lastName}"?`
      );

    if (!confirmed) {
      return;
    }

    this.professorService.deleteProfessor(id)
      .subscribe({

        next: () => {

          this.professors =
            this.professors.filter(
              professor =>
                professor.id !== id
            );

        },

        error: (error) => {

          console.error(error);

          this.errorMessage =
            error?.error?.message ??
            'Failed to delete professor';

        }

      });

  }

}
