import {Routes} from '@angular/router';
import {CourseListComponent} from './components/course-list/course-list.component';
import {CourseDetailComponent} from './components/course-detail/course-detail.component';
import {CourseCreateFormComponent} from './components/course-create-form/course-create-form.component';

export const COURSE_ROUTES: Routes = [

  {
    path: 'courses',
    component: CourseListComponent
  },

  {
    path: 'courses/new',
    component: CourseCreateFormComponent
  },

  {
    path: 'courses/update',
    component: CourseCreateFormComponent
  },

  {
    path: 'courses/:id',
    component: CourseDetailComponent
  }

]
