import {Gender} from '../../../core/enums/Gender';

export interface Professor {

  id: number,
  userId: number,
  firstName: string,
  lastName: string,
  gender: Gender,
  dateOfBirth: string,
  email: string,
  courseIds: number[]

}
