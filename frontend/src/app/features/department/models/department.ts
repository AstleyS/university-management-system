export interface Department {

  id: number,
  facultyId: number,
  name: string,
  code: string,
  description: string,
  coursesIds: number[]

}

export interface DepartmentCreateRequest {

  facultyId: number,
  name: string,
  code: string,
  description: string,

}
