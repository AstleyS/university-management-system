import { Routes } from '@angular/router';
import {AUTH_ROUTES} from './features/auth/auth.routes';
import {DASHBOARD_ROUTES} from './features/dashboard/dashboard.route';
import {COURSE_ROUTES} from './features/course/course.route';
import {STUDENT_ROUTES} from './features/student/student.route';

export const routes: Routes = [

  ...AUTH_ROUTES,
  ...DASHBOARD_ROUTES,
  ...COURSE_ROUTES,
  ...STUDENT_ROUTES,

  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  {
    path: '**',
    redirectTo: 'login'
  }

];
