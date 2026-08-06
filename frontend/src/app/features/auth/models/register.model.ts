import {Role} from '../../../core/enums/Role';

export interface RegisterRequest {
  username: string;
  password: string;
  // send as an array; backend will accept a collection (Set) from JSON array
  roles: Role[];
}

export interface RegisterResponse {
  id: number;
  username: string;
  role: Role[];
}
