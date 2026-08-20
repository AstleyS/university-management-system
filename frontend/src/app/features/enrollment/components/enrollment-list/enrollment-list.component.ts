import {Component, OnInit} from '@angular/core';
import {EnrollmentService} from '../../services/enrollment.service';
import {Enrollment} from '../../models/enrollment';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-enrollment-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './enrollment-list.component.html',
  styleUrl: './enrollment-list.component.scss'
})
export class EnrollmentListComponent implements OnInit {

  constructor(private enrollmentService: EnrollmentService) {}

  enrollments: Enrollment[] = []

  ngOnInit() {

    this.enrollmentService.getEnrollments().subscribe({

      next: (data) => {
        console.log(data)
        this.enrollments = data;
      },

      error: err => console.error(err)

    });
  }

  deleteEnrollment(id: number) {

    this.enrollmentService.deleteEnrollment(id).subscribe({

      next: () => {

        console.log('Enrollment deleted');
        this.enrollments = this.enrollments.filter(
          enrollment => enrollment.id !== id
        );
      },

      error: (error) => {
        console.error(error);
      }

    });
  }
}
