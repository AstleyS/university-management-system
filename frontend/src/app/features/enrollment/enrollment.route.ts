import {Routes} from '@angular/router';
import {EnrollmentListComponent} from './components/enrollment-list/enrollment-list.component';
import {EnrollmentCreateFormComponent} from './components/enrollment-create-form/enrollment-create-form.component';

export const ENROLLMENT_ROUTE: Routes = [

  {
    path: 'enrollments',
    component: EnrollmentListComponent
  },

  {
    path: 'enrollments/new',
    component: EnrollmentCreateFormComponent
  }

]
