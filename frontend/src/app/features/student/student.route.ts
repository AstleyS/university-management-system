import {Routes} from '@angular/router';
import {StudentListComponent} from './components/student-list/student-list.component';
import {StudentDetailComponent} from './components/student-detail/student-detail.component';
import {StudentFormComponent} from './components/student-form/student-form.component';

export const STUDENT_ROUTES: Routes = [

  {
    path: 'students',
    component: StudentListComponent
  },

  {
    path: 'students/:id',
    component: StudentDetailComponent
  },

  {
    path: 'students/new',
    component: StudentFormComponent
  },

  {
    path: 'students/update',
    component: StudentFormComponent
  }

]
