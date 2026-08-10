import {EnrollmentStatus} from '../../../core/enums/EnrollmentStatus';
import {SemesterSummary} from './SemesterSummary';
import {StudentSummary} from './StudentSummary';
import {CourseSummary} from './CourseSummary';

export interface Enrollment {

  id: number,
  semester: SemesterSummary,
  enrollmentDate: string,
  student: StudentSummary,
  course: CourseSummary,
  grade: number,
  enrollmentStatus: EnrollmentStatus

}
