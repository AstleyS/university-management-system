import {EnrollmentStatus} from '../../../core/enums/EnrollmentStatus';

export interface Enrollment {

  id: number,
  semesterId: number,
  enrollmentDate: string,
  studentId: number,
  courseId: number,
  grade: number,
  enrollmentStatus: EnrollmentStatus

}
