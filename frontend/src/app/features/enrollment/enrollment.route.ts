import {Routes} from '@angular/router';
import {EnrollmentListComponent} from './components/enrollment-list/enrollment-list.component';

export const ENROLLMENT_ROUTE: Routes = [

  {
    path: 'enrollments',
    component: EnrollmentListComponent
  }

]
