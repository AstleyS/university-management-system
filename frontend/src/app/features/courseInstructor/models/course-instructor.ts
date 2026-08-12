import {CourseSummary} from '../../course/models/CourseSummary';
import {ProfessorSummary} from '../../professor/models/ProfessorSummary';

export interface CourseInstructor {

  id: number;
  course: CourseSummary;
  professor: ProfessorSummary;

}

export interface CourseInstructorCreateRequest {

  courseId: number;
  professorId: number;

}


