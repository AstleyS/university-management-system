import {Routes} from '@angular/router';
import {CourseListComponent} from './components/course-list/course-list.component';
import {CourseDetailComponent} from './components/course-detail/course-detail.component';
import {CourseFormComponent} from './components/course-form/course-form.component';

export const COURSE_ROUTES: Routes = [

  {
    path: 'courses',
    component: CourseListComponent
  },

  {
    path: 'courses/new',
    component: CourseFormComponent
  },

  {
    path: 'courses/update',
    component: CourseFormComponent
  },

  {
    path: 'courses/:id',
    component: CourseDetailComponent
  }

]
