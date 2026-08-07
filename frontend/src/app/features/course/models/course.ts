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
