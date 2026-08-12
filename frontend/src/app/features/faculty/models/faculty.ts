import {Department} from '../../department/models/department';

export interface Faculty {

  id: number,
  name: string,
  code: string,
  description: string,
  departments: Department[]

}

export interface FacultyCreateRequest {

  name: string,
  code: string,
  description: string,

}
