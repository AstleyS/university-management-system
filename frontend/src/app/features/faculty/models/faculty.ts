export interface Faculty {

  id: number,
  name: string,
  code: string,
  description: string,
  departmentIds: number[]

}

export interface FacultyCreateRequest {

  name: string,
  code: string,
  description: string,

}
