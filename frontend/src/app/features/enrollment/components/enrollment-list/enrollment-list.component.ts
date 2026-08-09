import {Component, OnInit} from '@angular/core';
import {EnrollmentService} from '../../services/enrollment.service';
import {Enrollment} from '../../models/enrollment';

@Component({
  selector: 'app-enrollment-list',
  standalone: true,
  templateUrl: './enrollment-list.component.html',
  styleUrl: './enrollment-list.component.scss'
})
export class EnrollmentListComponent implements OnInit {

  constructor(private enrollmentService: EnrollmentService) {}

  enrollments?: Enrollment[]

  ngOnInit() {

    this.enrollmentService.getEnrollments().subscribe({

      next: (data) => {
        console.log(data)
        this.enrollments = data;
      },

      error: err => console.error(err)

    });
  }
}
