import {Gender} from '../../../core/enums/Gender';
import {CourseInstructor} from '../../courseInstructor/models/course-instructor';

export interface Professor {

  id: number,
  userId: number,
  firstName: string,
  lastName: string,
  gender: Gender,
  dateOfBirth: string,
  email: string,
  courses?: CourseInstructor[]

}

export interface ProfessorCreateRequest {

  firstName: string,
  lastName: string,
  gender: Gender,
  dateOfBirth: string,
  email: string,

}
