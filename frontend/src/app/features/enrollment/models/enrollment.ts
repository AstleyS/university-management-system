import {EnrollmentStatus} from '../../../core/enums/EnrollmentStatus';
import {SemesterEnrollment} from './SemesterEnrollment';
import {StudentEnrollment} from './StudentEnrollment';
import {CourseEnrollment} from './CourseEnrollment';

export interface Enrollment {

  id: number,
  semester: SemesterEnrollment,
  enrollmentDate: string,
  student: StudentEnrollment,
  course: CourseEnrollment,
  grade: number,
  enrollmentStatus: EnrollmentStatus

}
