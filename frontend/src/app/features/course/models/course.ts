export interface Course {

  id: number,
  departmentId: number,
  code: string,
  name: string,
  description: string,
  credits: number,
  enrollmentIds: number[]
  courseInstructors: number[]

}

export interface CourseCreateRequest {

  departmentId: number,
  code: string,
  name: string,
  description: string,
  credits: number

}

