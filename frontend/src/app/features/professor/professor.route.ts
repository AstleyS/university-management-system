import {Routes} from '@angular/router';
import {ProfessorListComponent} from './components/professor-list/professor-list.component';
import {ProfessorCreatFormComponent} from './components/professor-creat-form/professor-creat-form.component';

export const PROFESSOR_ROUTES: Routes = [

  {
    path: 'professors',
    component: ProfessorListComponent
  },

  {
    path: 'professors/new',
    component: ProfessorCreatFormComponent
  }

]
