import {EnrollmentStatus} from '../../../core/enums/EnrollmentStatus';
import {StudentSummary} from '../../student/models/StudentSummary';
import {CourseSummary} from '../../course/models/CourseSummary';
import {Semester} from '../../semester/models/semester';

export interface Enrollment {

  id: number,
  semester: Semester,
  enrollmentDate: string,
  student: StudentSummary,
  course: CourseSummary,
  grade: number,
  enrollmentStatus: EnrollmentStatus

}

export interface EnrollmentCreateRequest {

  studentId: number,
  courseId: number,
  semesterId: number,
  enrollmentDate: string,
  enrollmentStatus: EnrollmentStatus
  grade?: number,

}
