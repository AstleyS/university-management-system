import {Component, OnInit} from '@angular/core';
import {Department} from '../../models/department';
import {DepartmentService} from '../../services/department.service';

@Component({
  selector: 'app-department-list',
  standalone: true,
  templateUrl: './department-list.component.html',
  styleUrl: './department-list.component.scss'
})
export class DepartmentListComponent implements OnInit {

  constructor(private departmentService: DepartmentService) {}

  departments?: Department[];

  ngOnInit() {

    this.departmentService.getDepartments().subscribe({

      next: (data) => {
        this.departments = data;
      },

      error: err => console.error(err)

    });
  }





}
