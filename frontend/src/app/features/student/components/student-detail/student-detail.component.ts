import {Component, OnInit} from '@angular/core';
import {Student} from '../../models/student';
import {ActivatedRoute} from '@angular/router';
import {StudentService} from '../../services/student.service';
import {EnrollmentService} from '../../../enrollment/services/enrollment.service';

@Component({
  selector: 'app-student-detail',
  imports: [],
  templateUrl: './student-detail.component.html',
  styleUrl: './student-detail.component.scss'
})
export class StudentDetailComponent implements OnInit {

  student?: Student;

  loading = true;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private studentService: StudentService,
    private enrollmentService: EnrollmentService
  ) {}

  ngOnInit() {

    const id = Number(this.route.snapshot.paramMap.get('id'))
    if (!id) {
      this.errorMessage = 'Invalid student ID';
      this.loading = false;
      return;
    }

    this.studentService.getStudentById(id).subscribe({

      next: (data) => {
        console.log(`Student with id ${id}: `, data);
        this.loading = false;
        this.student = data;
      },

      error: (error) => {
        console.error(error);
        this.errorMessage = 'Failed to load student';
        this.loading = false;
      }
    });
  }
}
