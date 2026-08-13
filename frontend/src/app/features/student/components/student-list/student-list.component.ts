import {Component, OnInit} from '@angular/core';
import {Student} from '../../models/student';
import {StudentService} from '../../services/student.service';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './student-list.component.html',
  styleUrl: './student-list.component.scss'
})
export class StudentListComponent implements OnInit {

  students?: Student[]

  constructor(private studentService: StudentService) {}

  ngOnInit() {

    this.studentService.getStudents().subscribe({
      next: (data) => {
        console.log('students: ', data)
        this.students = data
      },
      error: (error) => console.error(error)
    })
  }

  onDeleteStudent(id: number) {
    this.studentService.deleteStudent(id)
  }

}
