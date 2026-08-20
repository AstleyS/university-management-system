import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { EnrollmentService } from '../../services/enrollment.service';
import { Enrollment } from '../../models/enrollment';

@Component({
  selector: 'app-enrollment-list',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule
  ],
  templateUrl: './enrollment-list.component.html',
  styleUrl: './enrollment-list.component.scss'
})
export class EnrollmentListComponent implements OnInit {

  enrollments: Enrollment[] = [];

  searchTerm = '';

  loading = true;
  errorMessage = '';

  constructor(
    private enrollmentService: EnrollmentService
  ) {}

  ngOnInit() {

    this.loadEnrollments();

  }


  loadEnrollments() {

    this.enrollmentService.getEnrollments()
      .subscribe({

        next: (data) => {

          console.log(data);

          this.enrollments = data;

          this.loading = false;

        },

        error: (error) => {

          console.error(error);

          this.errorMessage =
            'Failed to load enrollments';

          this.loading = false;

        }

      });

  }


  get filteredEnrollments(): Enrollment[] {

    const search =
      this.searchTerm
        .trim()
        .toLowerCase();

    if (!search) {
      return this.enrollments;
    }

    return this.enrollments.filter(
      enrollment => {

        const studentName =
          `${enrollment.student.firstName}
            ${enrollment.student.lastName}`
            .toLowerCase();

        const studentEmail =
          enrollment.student.email
            ?.toLowerCase() ?? '';

        const courseName =
          enrollment.course.name
            ?.toLowerCase() ?? '';

        const courseCode =
          enrollment.course.code
            ?.toLowerCase() ?? '';

        const semester =
          `${enrollment.semester.term}
            ${enrollment.semester.year}`
            .toLowerCase();

        const status =
          enrollment.enrollmentStatus
            ?.toString()
            .toLowerCase() ?? '';

        return (
          studentName.includes(search) ||
          studentEmail.includes(search) ||
          courseName.includes(search) ||
          courseCode.includes(search) ||
          semester.includes(search) ||
          status.includes(search)
        );

      }
    );

  }


  deleteEnrollment(id: number) {

    const enrollment =
      this.enrollments.find(
        enrollment =>
          enrollment.id === id
      );

    if (!enrollment) {
      return;
    }

    const confirmed = confirm(
      `Delete enrollment for ${
        enrollment.student.firstName
      } ${
        enrollment.student.lastName
      } in ${
        enrollment.course.code
      }?`
    );

    if (!confirmed) {
      return;
    }

    this.enrollmentService.deleteEnrollment(id)
      .subscribe({

        next: () => {

          console.log(
            'Enrollment deleted'
          );

          this.enrollments =
            this.enrollments.filter(
              enrollment =>
                enrollment.id !== id
            );

        },

        error: (error) => {

          console.error(error);

          this.errorMessage =
            error?.error?.message ??
            'Failed to delete enrollment';

        }
      });
  }
}
