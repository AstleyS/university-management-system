import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { Student } from '../../models/student';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule
  ],
  templateUrl: './student-list.component.html',
  styleUrl: './student-list.component.scss'
})
export class StudentListComponent implements OnInit {

  students: Student[] = [];

  searchTerm = '';

  loading = true;
  errorMessage = '';

  constructor(
    private studentService: StudentService
  ) {}

  ngOnInit() {

    this.studentService.getStudents().subscribe({

      next: (data) => {

        console.log('students:', data);
        this.students = data;
        this.loading = false;

      },

      error: (error) => {

        console.error(error);
        this.errorMessage = 'Failed to load students';
        this.loading = false;

      }

    });
  }


  get filteredStudents(): Student[] {

    const search = this.searchTerm
        .trim()
        .toLowerCase();

    if (!search) {
      return this.students;
    }

    return this.students.filter(student => {

      const fullName =
        `${student.firstName} ${student.lastName}`
          .toLowerCase();

      const email =
        student.email
          .toLowerCase();

      return (
        fullName.includes(search) ||
        email.includes(search)
      );

    });

  }


  onDeleteStudent(id: number) {

    const student =
      this.students.find(
        student => student.id === id
      );

    if (!student) {
      return;
    }

    const confirmed = confirm(
      `Delete student "${student.firstName} ${student.lastName}"?`
    );

    if (!confirmed) {
      return;
    }

    this.studentService.deleteStudent(id)
      .subscribe({

        next: () => {

          console.log('Student deleted');

          this.students =
            this.students.filter(
              student => student.id !== id
            );

        },

        error: (error) => {

          console.error(error);

          this.errorMessage =
            error?.error?.message ??
            'Failed to delete student';

        }

      });
  }
}
