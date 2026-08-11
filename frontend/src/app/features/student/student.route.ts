import {Routes} from '@angular/router';
import {StudentListComponent} from './components/student-list/student-list.component';
import {StudentDetailComponent} from './components/student-detail/student-detail.component';
import {StudentCreateFormComponent} from './components/student-create-form/student-create-form.component';

export const STUDENT_ROUTES: Routes = [

  {
    path: 'students',
    component: StudentListComponent
  },

  {
    path: 'students/new',
    component: StudentCreateFormComponent
  },

  {
    path: 'students/:id',
    component: StudentDetailComponent
  },

  {
    path: 'students/update',
    component: StudentCreateFormComponent
  }

]
