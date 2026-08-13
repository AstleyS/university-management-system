import {Gender} from '../../../core/enums/Gender';
import {Enrollment} from '../../enrollment/models/enrollment';

export interface Student {

  id: number,
  userId: number,
  firstName: string,
  lastName: string,
  gender: Gender,
  dateOfBirth: string,
  email: string,
  enrollments: Enrollment[]

}

export interface StudentRequest {

  firstName: string;
  lastName: string;
  gender: Gender;
  dateOfBirth: string;
  email: string
}
