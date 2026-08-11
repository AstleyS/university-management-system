import {Gender} from '../../../core/enums/Gender';

export interface Student {

  id: number,
  userId: number,
  firstName: string,
  lastName: string,
  gender: Gender,
  dateOfBirth: string,
  email: string,
  enrollmentIds: number[]

}

export interface StudentRequest {

  firstName: string;
  lastName: string;
  gender: Gender;
  dateOfBirth: string;
  email: string
}
