import {Component, OnInit} from '@angular/core';
import {Student} from '../../models/student';
import {StudentService} from '../../services/student.service';

@Component({
  selector: 'app-student-list',
  standalone: true,
  templateUrl: './student-list.component.html',
  styleUrl: './student-list.component.scss'
})
export class StudentListComponent implements OnInit {

  students?: Student[]

  constructor(private studentService: StudentService) {}

  ngOnInit() {

    this.studentService.getStudents().subscribe({
      next: (data) => {
        this.students = data
        console.log(data)
      },
      error: (error) => console.error(error)
    })
  }

}
