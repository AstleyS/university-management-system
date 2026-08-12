import {Faculty} from '../../faculty/models/faculty';
import {Course} from '../../course/models/course';

export interface Department {

  id: number,
  faculty: Faculty,
  name: string,
  code: string,
  description: string,
  courses: Course[]

}

export interface DepartmentCreateRequest {

  facultyId: number,
  name: string,
  code: string,
  description: string,

}

export interface DepartmentCreateRequest {

  facultyId: number,
  name: string,
  code: string,
  description: string,

}
