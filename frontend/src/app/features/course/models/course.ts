import {Department} from '../../department/models/department';
import {Enrollment} from '../../enrollment/models/enrollment';
import {CourseInstructor} from '../../courseInstructor/models/course-instructor';

export interface Course {

  id: number,
  department: Department,
  code: string,
  name: string,
  description: string,
  credits: number,
  enrollments: Enrollment[]
  courseInstructors: CourseInstructor[]

}

export interface CourseCreateRequest {

  departmentId: number,
  code: string,
  name: string,
  description: string,
  credits: number

}

